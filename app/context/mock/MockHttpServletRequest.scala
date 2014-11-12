package context.mock

import java.io.BufferedReader
import java.security.Principal
import java.util
import java.util.Locale
import javax.servlet._
import javax.servlet.http._

import play.Play

class MockHttpServletRequest extends HttpServletRequest {

	override def getAuthType: String = ???

	override def isRequestedSessionIdFromURL: Boolean = ???

	override def getRemoteUser: String = ???

	override def getUserPrincipal: Principal = ???

	override def getParts: util.Collection[Part] = ???

	override def getHeaderNames: util.Enumeration[String] = ???

	override def getPathInfo: String = ???

	override def getRequestURL: StringBuffer = ???

	override def getCookies: Array[Cookie] = ???

	override def getQueryString: String = ???

	override def getPart(p1: String): Part = ???

	override def getContextPath: String = Play.application.configuration.getString("application.context", "")

	override def getServletPath: String = ???

	override def getRequestURI: String = ???

	override def getPathTranslated: String = ???

	override def getIntHeader(p1: String): Int = ???

	override def getHeaders(p1: String): util.Enumeration[String] = ???

	override def changeSessionId(): String = ???

	override def authenticate(p1: HttpServletResponse): Boolean = ???

	override def getRequestedSessionId: String = ???

	override def logout(): Unit = ???

	override def isRequestedSessionIdFromUrl: Boolean = ???

	override def upgrade[T <: HttpUpgradeHandler](p1: Class[T]): T = ???

	override def isRequestedSessionIdValid: Boolean = ???

	override def getSession(p1: Boolean): HttpSession = ???

	override def getSession: HttpSession = ???

	override def getMethod: String = ???

	override def getDateHeader(p1: String): Long = ???

	override def isUserInRole(p1: String): Boolean = ???

	override def isRequestedSessionIdFromCookie: Boolean = ???

	override def login(p1: String, p2: String): Unit = ???

	override def getHeader(p1: String): String = ???

	override def getParameter(p1: String): String = ???

	override def getRequestDispatcher(p1: String): RequestDispatcher = ???

	override def isAsyncStarted: Boolean = ???

	override def startAsync(): AsyncContext = ???

	override def startAsync(p1: ServletRequest, p2: ServletResponse): AsyncContext = ???

	override def getRealPath(p1: String): String = ???

	override def getLocale: Locale = ???

	override def getRemoteHost: String = ???

	override def getParameterNames: util.Enumeration[String] = ???

	override def isSecure: Boolean = ???

	override def getLocalPort: Int = ???

	override def getContentLengthLong: Long = ???

	override def getAttribute(p1: String): AnyRef = ???

	override def removeAttribute(p1: String): Unit = ???

	override def getLocalAddr: String = ???

	override def getAsyncContext: AsyncContext = ???

	override def getCharacterEncoding: String = ???

	override def setCharacterEncoding(p1: String): Unit = ???

	override def getParameterValues(p1: String): Array[String] = ???

	override def getRemotePort: Int = ???

	override def getServerName: String = ???

	override def getProtocol: String = ???

	override def getLocales: util.Enumeration[Locale] = ???

	override def getAttributeNames: util.Enumeration[String] = ???

	override def setAttribute(p1: String, p2: scala.Any): Unit = ???

	override def getRemoteAddr: String = ???

	override def getLocalName: String = ???

	override def getDispatcherType: DispatcherType = ???

	override def getContentLength: Int = ???

	override def getServerPort: Int = ???

	override def getContentType: String = ???

	override def getReader: BufferedReader = ???

	override def isAsyncSupported: Boolean = ???

	override def getServletContext: ServletContext = ???

	override def getScheme: String = ???

	override def getParameterMap: util.Map[String, Array[String]] = ???

	override def getInputStream: ServletInputStream = ???
}
