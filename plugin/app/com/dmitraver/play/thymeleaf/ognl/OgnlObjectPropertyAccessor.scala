package com.dmitraver.play.thymeleaf.ognl

import java.beans.IntrospectionException
import java.util

import ognl.{OgnlException, OgnlRuntime, ObjectPropertyAccessor, OgnlContext}

import scala.annotation.target
import scala.collection.JavaConversions._

class OgnlObjectPropertyAccessor extends ObjectPropertyAccessor {
	override def getPossibleProperty(context: util.Map[_, _], target: scala.Any, name: String): AnyRef = {
		if(isCaseClass(target)) {
			getCaseClassFieldValueByName(target, name) match {
				case Some(x) => return x.asInstanceOf[AnyRef]
				case None =>
			}
		}

		var result: Object = null
		val ognlContext: OgnlContext = context.asInstanceOf[OgnlContext]

		try {
			result = OgnlRuntime.getMethodValue( ognlContext, target, name, true )
			if (result == OgnlRuntime.NotFound ) {
				result = OgnlRuntime.getFieldValue( ognlContext, target, name, true )
			}
		}
		catch {
			case ex: IntrospectionException => throw new OgnlException( name, ex )
			case ex: OgnlException => throw ex
			case ex: Exception => throw new OgnlException( name, ex )
		}

		result
	}

	def getCaseClassFieldValueByName(targetClass: Any, fieldName: String): Option[Any] = {
		val productInstance = targetClass.asInstanceOf[Product]
		val fieldsNameToValueMap = productInstance.getClass.getDeclaredFields.map( _.getName )
						.zip(productInstance.productIterator.to ).toMap
		fieldsNameToValueMap.get(fieldName)
	}

	def isCaseClass(instance: Any) = {
		import reflect.runtime.universe._
		val typeMirror = runtimeMirror(instance.getClass.getClassLoader)
		val instanceMirror = typeMirror.reflect(instance)
		val symbol = instanceMirror.symbol
		symbol.isCaseClass
	}

	override def setPossibleProperty(context: util.Map[_, _], target: scala.Any, name: String, value: scala.Any): AnyRef = super.setPossibleProperty(context, target, name, value)

	override def hasGetProperty(context: OgnlContext, target: scala.Any, oname: scala.Any): Boolean = super.hasGetProperty(context, target, oname)

	override def hasGetProperty(context: util.Map[_, _], target: scala.Any, oname: scala.Any): Boolean = super.hasGetProperty(context, target, oname)

	override def hasSetProperty(context: OgnlContext, target: scala.Any, oname: scala.Any): Boolean = super.hasSetProperty(context, target, oname)

	override def hasSetProperty(context: util.Map[_, _], target: scala.Any, oname: scala.Any): Boolean = super.hasSetProperty(context, target, oname)

	override def getProperty(context: util.Map[_, _], target: scala.Any, oname: scala.Any): AnyRef = {
		val result = super.getProperty(context, target, oname)
		result match {
			case c: Seq[_] => seqAsJavaList(c)
			case c: Map[_, _] => mapAsJavaMap(c)
			case c: Set[_] => setAsJavaSet(c)
			case _ => result
		}
	}

	override def setProperty(context: util.Map[_, _], target: scala.Any, oname: scala.Any, value: scala.Any): Unit = super.setProperty(context, target, oname, value)

	override def getPropertyClass(context: OgnlContext, target: scala.Any, index: scala.Any): Class[_] = super.getPropertyClass(context, target, index)

	override def getSourceAccessor(context: OgnlContext, target: scala.Any, index: scala.Any): String = super.getSourceAccessor(context, target, index)

	override def getSourceSetter(context: OgnlContext, target: scala.Any, index: scala.Any): String = super.getSourceSetter(context, target, index)
}
