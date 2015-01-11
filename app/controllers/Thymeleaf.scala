package controllers

import java.io.StringWriter
import context.PlayContext
import dialect.PlayDialect
import l18n.PlayMessageResolver
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.VariablesMap
import play.Play
import play.api.i18n.Lang
import play.api.mvc.{Flash, Session}
import play.twirl.api.Html
import template.{PlayResourceResolver, PlayTemplateResolver}
import wrappers._
import scala.collection.JavaConversions._

object Thymeleaf {

	private val THYMELEAF_CACHE_ENABLED_PROPERTY_KEY = "thymeleaf.cache.enabled"
	private val THYMELEAF_CACHE_ENABLED_PROPERTY_DEFAULT_VALUE = true

	private val THYMELEAF_CACHE_TTL_IN_MILLIS_PROPERTY_KEY = "thymeleaf.cache.ttlms"
	private val THYMELEAF_CACHE_TTL_IN_MILLIS_PROPERTY_DEFAULT_VALUE = null

	private val THYMELEAF_TEMPLATE_MODE_PROPERTY_KEY = "thymeleaf.template.mode"
	private val THYMELEAF_TEMPLATE_MODE_PROPERTY_DEFAULT_VALUE = "XHTML"

	private val THYMELEAF_TEMPLATE_PREFIX_PROPERTY_KEY = "thymeleaf.template.prefix"
	private val THYMELEAF_TEMPLATE_PREFIX_PROPERTY_DEFAULT_VALUE = "public"

	private val THYMELEAF_TEMPLATE_SUFFIX_PROPERTY_KEY = "thymeleaf.template.suffix"
	private val THYMELEAF_TEMPLATE_SUFFIX_PROPERTY_DEFAULT_VALUE = ".html"

	private val templateEngine = new TemplateEngine
	private val resourceResolver = new PlayResourceResolver
	private val messageResolver = new PlayMessageResolver
	private val templateResolver = new PlayTemplateResolver(resourceResolver)

	templateResolver.setCacheable(Play.application.configuration.
					getBoolean(THYMELEAF_CACHE_ENABLED_PROPERTY_KEY, THYMELEAF_CACHE_ENABLED_PROPERTY_DEFAULT_VALUE))
	templateResolver.setCacheTTLMs(Play.application.configuration.
					getLong(THYMELEAF_CACHE_TTL_IN_MILLIS_PROPERTY_KEY, THYMELEAF_CACHE_TTL_IN_MILLIS_PROPERTY_DEFAULT_VALUE))
	templateResolver.setTemplateMode(Play.application.configuration.
					getString(THYMELEAF_TEMPLATE_MODE_PROPERTY_KEY, THYMELEAF_TEMPLATE_MODE_PROPERTY_DEFAULT_VALUE))
	templateResolver.setPrefix(Play.application.configuration.
					getString(THYMELEAF_TEMPLATE_PREFIX_PROPERTY_KEY, THYMELEAF_TEMPLATE_PREFIX_PROPERTY_DEFAULT_VALUE))
	templateResolver.setSuffix(Play.application.configuration.
					getString(THYMELEAF_TEMPLATE_SUFFIX_PROPERTY_KEY, THYMELEAF_TEMPLATE_SUFFIX_PROPERTY_DEFAULT_VALUE))

	templateEngine.setTemplateResolver(templateResolver)
	templateEngine.addDialect(new PlayDialect)
	templateEngine.setMessageResolver(messageResolver)


	def render(templateName: String, temlateObjects: Map[String, AnyRef] = Map())
						(implicit language: Lang, flash: Flash = Flash(), session: Session = Session()): Html = {
		messageResolver.setLanguage(language)

		val templateVariables = new VariablesMap[String, AnyRef]()
		temlateObjects.foreach{obj =>
			val templateObject = obj._2 match {
				case c: Seq[_] => seqAsJavaList(c)
				case c: Map[_, _] => mapAsJavaMap(c)
				case c: Set[_] => setAsJavaSet(c)
				case _ => obj._2
			}

			templateVariables.put(obj._1, templateObject)}

		templateVariables.put("session", SessionMap(session))
		templateVariables.put("flash", FlashMap(flash))

		val context = new PlayContext(language.toLocale, templateVariables)
		val stringWriter = new StringWriter
		templateEngine.process(templateName, context, stringWriter)
		Html(stringWriter.toString)
	}
}

