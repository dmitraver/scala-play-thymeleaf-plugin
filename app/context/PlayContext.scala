package context

import java.util.Locale
import javax.servlet.ServletContext
import javax.servlet.http.{HttpServletRequest, HttpServletResponse, HttpSession}

import context.mock.{MockHttpServletRequest, MockHttpSession}
import org.thymeleaf.context.{Context, IWebContext, VariablesMap}

class PlayContext(locale: Locale, variables: VariablesMap[String, AnyRef]) extends Context(locale, variables) with IWebContext {

	val servletRequest = new MockHttpServletRequest

	override def getHttpServletRequest: HttpServletRequest = servletRequest

	override def getHttpSession: HttpSession = new MockHttpSession

	override def getRequestParameters: VariablesMap[String, Array[String]] = new VariablesMap[String, Array[String]]()

	override def getApplicationAttributes: VariablesMap[String, AnyRef] = new VariablesMap[String, AnyRef]()

	override def getHttpServletResponse: HttpServletResponse = null

	override def getSessionAttributes: VariablesMap[String, AnyRef] = new VariablesMap[String, AnyRef]()

	override def getServletContext: ServletContext = null

	override def getRequestAttributes: VariablesMap[String, AnyRef] = new VariablesMap[String, AnyRef]()
}
