package controllers

import java.io.StringWriter
import java.util
import java.util.Locale

import l18n.PlayMessageResolver
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import play.api.i18n.Lang
import play.api.mvc.{Flash, Session}
import play.twirl.api.Html
import template.{PlayResourceResolver, PlayTemplateResolver}
import wrappers._

object Thymeleaf {

	private val templateEngine = new TemplateEngine
	private val messageResolver = new PlayMessageResolver
	private val resourceResolver = new PlayResourceResolver
	private val templateResolver = new PlayTemplateResolver(resourceResolver)

	templateResolver.setCacheable(play.Play.application().configuration().
					getBoolean("thymeleaf.cache.enabled", true))
	templateEngine.setTemplateResolver(templateResolver)
	templateEngine.setMessageResolver(messageResolver)


	def render(templateName: String, objects: Map[String, Object] = Map())
						(implicit language: Lang, flash: Flash = Flash(), session: Session = Session()): Html = {
		messageResolver.setLang(language)

		val map = new util.HashMap[String, Object]()
		objects.foreach(o => map.put(o._1, o._2))
		map.put("session", SessionMap(session))
		map.put("flash", FlashMap(flash))

		val context = new Context(language.toLocale, map)
		val stringWriter = new StringWriter
		templateEngine.process(templateName, context, stringWriter)
		Html(stringWriter.toString)
	}


}

