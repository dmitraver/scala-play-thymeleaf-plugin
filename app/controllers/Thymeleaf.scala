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

object Thymeleaf {

	private val templateEngine = new TemplateEngine
	private val resourceResolver = new PlayResourceResolver
	private val templateResolver = new PlayTemplateResolver(resourceResolver)

	templateResolver.setCacheable(Play.application.configuration.
					getBoolean("thymeleaf.cache.enabled", true))
	templateEngine.setTemplateResolver(templateResolver)
	templateEngine.addDialect(new PlayDialect)


	def render(templateName: String, objects: Map[String, AnyRef] = Map())
						(implicit language: Lang, flash: Flash = Flash(), session: Session = Session()): Html = {
		templateEngine.setMessageResolver(new PlayMessageResolver(language))

		val map = new VariablesMap[String, AnyRef]()
		objects.foreach(o => map.put(o._1, o._2))
		map.put("session", SessionMap(session))
		map.put("flash", FlashMap(flash))

		val context = new PlayContext(language.toLocale, map)
		val stringWriter = new StringWriter
		templateEngine.process(templateName, context, stringWriter)
		Html(stringWriter.toString)
	}


}

