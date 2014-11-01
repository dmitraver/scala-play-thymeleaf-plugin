package controllers

import java.io.StringWriter
import java.util.Locale

import l18n.PlayMessageResolver
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import play.twirl.api.Html
import template.{PlayResourceResolver, PlayTemplateResolver}
import play.api.i18n.Lang

import scala.collection.JavaConversions._

object Thymeleaf {

	private val templateEngine = new TemplateEngine
	private val messageResolver = new PlayMessageResolver
	private val resourceResolver = new PlayResourceResolver
	private val templateResolver = new PlayTemplateResolver(resourceResolver)

	templateResolver.setCacheable(play.Play.application().configuration().
					getBoolean("thymeleaf.cache.enabled", true))
	templateEngine.setTemplateResolver(templateResolver)
	templateEngine.setMessageResolver(messageResolver)


	def render(templateName: String, objects: Map[String, Object] = Map())(implicit language: Lang): Html = {
		messageResolver.setLang(language)
		val stringWriter = new StringWriter
		val map: java.util.Map[String, Object] = objects
		val context = new Context(new Locale("en"), map)
		templateEngine.process(templateName, context, stringWriter)
		Html(stringWriter.toString)
	}


}

