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

	private val templateEngine = new TemplateEngine
	private val resourceResolver = new PlayResourceResolver
	private val messageResolver = new PlayMessageResolver
	private val templateResolver = new PlayTemplateResolver(resourceResolver)

	templateResolver.setCacheable(Play.application.configuration.
					getBoolean("thymeleaf.cache.enabled", true))
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

			templateVariables.put(obj._1, obj)}

		templateVariables.put("session", SessionMap(session))
		templateVariables.put("flash", FlashMap(flash))

		val context = new PlayContext(language.toLocale, templateVariables)
		val stringWriter = new StringWriter
		templateEngine.process(templateName, context, stringWriter)
		Html(stringWriter.toString)
	}
}

