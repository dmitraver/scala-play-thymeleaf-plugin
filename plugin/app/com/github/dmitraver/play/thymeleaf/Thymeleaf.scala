package com.github.dmitraver.play.thymeleaf

import java.io.StringWriter

import com.github.dmitraver.play.thymeleaf.context.PlayContext
import com.github.dmitraver.play.thymeleaf.dialect.PlayDialect
import com.github.dmitraver.play.thymeleaf.l18n.PlayMessageResolver
import com.github.dmitraver.play.thymeleaf.template.{PlayTemplateResolver, PlayResourceResolver}
import com.github.dmitraver.play.thymeleaf.wrappers.{FlashMap, SessionMap}
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.VariablesMap
import play.Play
import play.api.i18n.Lang
import play.api.mvc.{Flash, Session}
import play.twirl.api.Html

/**
 * Main class to use Thymeleaf as a template engine
 */
object Thymeleaf {

	private val THYMELEAF_CACHE_ENABLED_PROPERTY_KEY = "thymeleaf.cache.enabled"
	private val THYMELEAF_CACHE_ENABLED_PROPERTY_DEFAULT_VALUE = false

	private val THYMELEAF_CACHE_TTL_IN_MILLIS_PROPERTY_KEY = "thymeleaf.cache.ttlms"
	private val THYMELEAF_CACHE_TTL_IN_MILLIS_PROPERTY_DEFAULT_VALUE = null

	private val THYMELEAF_TEMPLATE_MODE_PROPERTY_KEY = "thymeleaf.template.mode"
	private val THYMELEAF_TEMPLATE_MODE_PROPERTY_DEFAULT_VALUE = "XHTML"

	private val THYMELEAF_TEMPLATE_PREFIX_PROPERTY_KEY = "thymeleaf.template.prefix"
	private val THYMELEAF_TEMPLATE_PREFIX_PROPERTY_DEFAULT_VALUE = "public/"

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
	templateEngine.setMessageResolver(messageResolver)
	templateEngine.addDialect(new PlayDialect)

	/**
	 * Renders a template using specified parameters.
	 * @param templateName name of the template to render. By default module expects templates to be
	 *                     stored in public/ directory. You can specify another path to your templates by
	 *                     overriding thymeleaf.template.prefix configuration property in application.conf
	 * @param templateObjects map of named objects(variables) that will be available to use from expressions
	 *                        in the executed template
	 * @param language language to be used for message externalization (internationalization)
	 * @param flash flash scope that can be accessed in templates as "flash" named variable
	 * @param session session scope that can be accessed in templates as "session" named variable
	 * @return rendered template as [[play.twirl.api.Html]]
	 */
	def render(templateName: String, templateObjects: Map[String, AnyRef] = Map())
						(implicit language: Lang, flash: Flash = Flash(), session: Session = Session()): Html = {
		messageResolver.setLanguage(language)

		val templateVariables = new VariablesMap[String, AnyRef]()
		templateObjects.foreach(obj => templateVariables.put(obj._1, obj._2))

		templateVariables.put("session", SessionMap(session))
		templateVariables.put("flash", FlashMap(flash))

		val context = new PlayContext(language.toLocale, templateVariables)
		val stringWriter = new StringWriter
		templateEngine.process(templateName, context, stringWriter)
		Html(stringWriter.toString)
	}
}

