package controllers

import java.io.StringWriter
import java.util
import java.util.Locale
import collection.JavaConversions._

import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import play.twirl.api.Html
import template.{PlayResourceResolver, PlayTemplateResolver}

object Thymeleaf {

	private val templateEngine = new TemplateEngine
	private val resourceResolver = new PlayResourceResolver
	private val templateResolver = new PlayTemplateResolver(resourceResolver)

	templateResolver.setCacheable(play.Play.application().configuration().
					getBoolean("thymeleaf.cache.enabled", true))
	templateEngine.setTemplateResolver(templateResolver)


	def render(templateName: String, objects: Map[String, Object] = Map()): Html = {
		val stringWriter = new StringWriter
		val map: java.util.Map[String, Object] = objects
		val context = new Context(new Locale("en"), map)
		templateEngine.process(templateName, context, stringWriter)
		Html(stringWriter.toString)
	}
}

