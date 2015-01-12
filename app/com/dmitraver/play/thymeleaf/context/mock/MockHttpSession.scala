package com.dmitraver.play.thymeleaf.context.mock

import java.util
import javax.servlet.ServletContext
import javax.servlet.http.{HttpSession, HttpSessionContext}

class MockHttpSession extends HttpSession {

	override def getCreationTime: Long = ???

	override def getValue(p1: String): AnyRef = ???

	override def isNew: Boolean = ???

	override def getValueNames: Array[String] = ???

	override def getLastAccessedTime: Long = ???

	override def putValue(p1: String, p2: scala.Any): Unit = ???

	override def getSessionContext: HttpSessionContext = ???

	override def getAttribute(p1: String): AnyRef = ???

	override def removeAttribute(p1: String): Unit = ???

	override def getId: String = ???

	override def setMaxInactiveInterval(p1: Int): Unit = ???

	override def getAttributeNames: util.Enumeration[String] = ???

	override def setAttribute(p1: String, p2: scala.Any): Unit = ???

	override def invalidate(): Unit = ???

	override def removeValue(p1: String): Unit = ???

	override def getServletContext: ServletContext = ???

	override def getMaxInactiveInterval: Int = ???
}
