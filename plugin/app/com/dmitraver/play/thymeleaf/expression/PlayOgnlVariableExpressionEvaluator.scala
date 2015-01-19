package com.dmitraver.play.thymeleaf.expression

import java.util.Collections
import javassist.{ClassPool, CtClass, CtMethod, LoaderClassPath}

import com.dmitraver.play.thymeleaf.converter.OgnlTypeConverter
import com.dmitraver.play.thymeleaf.ognl.OgnlObjectPropertyAccessor
import ognl._
import org.thymeleaf.Configuration
import org.thymeleaf.cache.{ICache, ICacheManager}
import org.thymeleaf.context.{IContext, IContextVariableRestriction, IProcessingContext, VariablesMap}
import org.thymeleaf.exceptions.TemplateProcessingException
import org.thymeleaf.expression.ExpressionEvaluatorObjects
import org.thymeleaf.standard.expression._
import org.thymeleaf.util.{ClassLoaderUtils, EvaluationUtil}

import scala.collection.JavaConversions._

class PlayOgnlVariableExpressionEvaluator extends IStandardVariableExpressionEvaluator {

	val OGNL_CACHE_PREFIX = "{ognl}"

	if (!PlayOgnlVariableExpressionEvaluator.booleanFixApplied && shouldApplyOgnlBooleanFix()) {
		PlayOgnlVariableExpressionEvaluator.applyOgnlBooleanFix()
		PlayOgnlVariableExpressionEvaluator.booleanFixApplied = true
	}

	/**
	 * <p>
	 *   Determines whether a fix should be applied to OGNL in order
	 *   to evaluate Strings as booleans in the same way as
	 *   Thymeleaf does ('false', 'off' and 'no' are actually "false"
	 *   instead of OGNL's default "true").
	 * </p>
	 *
	 * @return whether the OGNL boolean fix should be applied or not.
	 */
	protected def shouldApplyOgnlBooleanFix():Boolean = true

	override def evaluate(configuration: Configuration, processingContext: IProcessingContext, expression: String, expContext: StandardExpressionExecutionContext, useSelectionAsRoot: Boolean): AnyRef = {

		try {
			/*if (PlayOgnlVariableExpressionEvaluator.logger.isTraceEnabled) {
				PlayOgnlVariableExpressionEvaluator.logger.trace("[THYMELEAF][{}] OGNL expression: evaluating expression \"{}\" on target", TemplateEngine.threadIndex(), expression)
			}*/

			var expressionTree: AnyRef = null
			var cache: ICache[String, AnyRef] = null

			if (configuration != null) {
				val cacheManager: ICacheManager = configuration.getCacheManager
				if (cacheManager != null) {
					cache = cacheManager.getExpressionCache
					if (cache != null) {
						expressionTree = cache.get(OGNL_CACHE_PREFIX + expression)
					}
				}
			}

			if (expressionTree == null) {
				expressionTree = ognl.Ognl.parseExpression(expression)
				if (cache != null && null != expressionTree) {
					cache.put(OGNL_CACHE_PREFIX + expression, expressionTree)
				}
			}

			var contextVariables: java.util.Map[String, AnyRef] = processingContext.getExpressionObjects

			val additionalContextVariables: java.util.Map[String, AnyRef] = computeAdditionalContextVariables(processingContext)
			if (additionalContextVariables != null) {
				contextVariables.putAll(additionalContextVariables)
			}

			val evaluationRoot: AnyRef = if(useSelectionAsRoot) processingContext.getExpressionSelectionEvaluationRoot
																	 else processingContext.getExpressionEvaluationRoot

			setVariableRestrictions(expContext, evaluationRoot, contextVariables)

			val context: OgnlContext = new OgnlContext(contextVariables)
			//context.setClassResolver(this.classResolver);
			context.setTypeConverter(new OgnlTypeConverter)
			OgnlRuntime.setPropertyAccessor(classOf[Object], new OgnlObjectPropertyAccessor)

			var result: AnyRef = Ognl.getValue(expressionTree, context, evaluationRoot)

			result  = result match {
				case c: Seq[_] => seqAsJavaList(c)
				case c: Map[_, _] => mapAsJavaMap(c)
				case c: Set[_] => setAsJavaSet(c)
				case _ => result
			}

			if (!expContext.getPerformTypeConversion) {
				return result
			}

			val conversionService: IStandardConversionService = StandardExpressions.getConversionService(configuration)

			conversionService.convert(configuration, processingContext, result, classOf[String])
		} catch {
			case e: OgnlException => throw new TemplateProcessingException(
				"Exception evaluating OGNL expression: \"" + expression + "\"", e)
		}

	}

	protected def computeAdditionalContextVariables(processingContext: IProcessingContext): java.util.Map[String, AnyRef] = Collections.emptyMap()


	protected def setVariableRestrictions(expContext: StandardExpressionExecutionContext, evaluationRoot: Any, contextVariables: java.util.Map[String, AnyRef]) = {
		val restrictions: java.util.List[IContextVariableRestriction] = if(expContext.getForbidRequestParameters)
																																			StandardVariableRestrictions.REQUEST_PARAMETERS_FORBIDDEN
		        																												else null

		val context: AnyRef = contextVariables.get(ExpressionEvaluatorObjects.CONTEXT_VARIABLE_NAME)
		if (context != null && context.isInstanceOf[IContext]) {
			val variablesMap: VariablesMap[_,_] = context.asInstanceOf[IContext].getVariables
			variablesMap.setRestrictions(restrictions)
		}

		if (evaluationRoot != null && evaluationRoot.isInstanceOf[VariablesMap[_,_]]) {
			evaluationRoot.asInstanceOf[VariablesMap[_,_]].setRestrictions(restrictions)
		}
	}

	override def toString: String = "OGNL"
}

object PlayOgnlVariableExpressionEvaluator {
	var booleanFixApplied = false

	//val logger: Logger = LoggerFactory.getLogger(classOf[OgnlVariableExpressionEvaluator])

	def fixBooleanValue(value: Any): Boolean =  {
		// This specifies how evaluation to boolean should be done *INSIDE* OGNL expressions, so the conversion
		// service does not really apply at this point (it will be applied later, on the Standard -not OGNL- expr.)
		EvaluationUtil.evaluateAsBoolean(value)
	}

	private def applyOgnlBooleanFix() = {

		try {

			val classLoader: ClassLoader =
						ClassLoaderUtils.getClassLoader(classOf[PlayOgnlVariableExpressionEvaluator])

			val pool: ClassPool = new ClassPool(true)
			pool.insertClassPath(new LoaderClassPath(classLoader))

			val params: Array[CtClass] = Array(pool.get(classOf[Object].getName))

			// We must load by class name here instead of "OgnlOps.class.getName()" because
			// the latter would cause the class to be loaded and therefore it would not be
			// possible to modify it.
			val ognlClass: CtClass = pool.get("ognl.OgnlOps")
			val fixClass:  CtClass  = pool.get(classOf[OgnlVariableExpressionEvaluator].getName)

			val ognlMethod: CtMethod =
						ognlClass.getDeclaredMethod("booleanValue", params)
			val fixMethod: CtMethod =
						fixClass.getDeclaredMethod("fixBooleanValue", params)

		ognlMethod.setBody(fixMethod, null)

		// Pushes the class to the class loader, effectively making it
		// load the modified version instead of the original one.
		ognlClass.toClass(classLoader, null);

		} catch {
			case e: Exception =>
			// Any exceptions here will be consumed and converted into log messages.
			// An exception at this point could be caused by multiple situations that
			// should not suppose the stop of the framework's initialization.
			// If the fix cannot not applied, an INFO message is issued and initialization
			// continues normally.
			/*if (logger.isTraceEnabled) {
				logger.trace(
					"Thymeleaf was not able to apply a fix on OGNL's boolean evaluation " +
									"that would have enabled OGNL to evaluate Strings as booleans (e.g. in " +
									"\"th:if\") in exactly the same way as Thymeleaf itself or Spring EL ('false', " +
									"'off' and 'no' should be considered \"false\"). This did not stop the " +
									"initialization process.", e)
			} else {
				logger.info(
					"Thymeleaf was not able to apply a fix on OGNL's boolean evaluation " +
									"that would have enabled OGNL to evaluate Strings as booleans (e.g. in " +
									"\"th:if\") in exactly the same way as Thymeleaf itself or Spring EL ('false', " +
									"'off' and 'no' should be considered \"false\"). This did not stop the " +
									"initialization process. Exception raised was " + e.getClass.getName +
									": " + e.getMessage + " [Set the log to TRACE for the complete exception stack trace]")
			}*/
		}

	}
}


