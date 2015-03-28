package com.github.dmitraver.play.thymeleaf.converter

import java.util

import ognl.DefaultTypeConverter

import scala.collection.JavaConversions._

/**
 * Class extends [[ognl.DefaultTypeConverter]] to support
 * conversion from Scala collections to Java collections counterparts.
 */
class OgnlTypeConverter extends DefaultTypeConverter {
	override def convertValue(context: util.Map[_, _], value: scala.Any, toType: Class[_]): AnyRef = {
		value match {
			case c: Seq[_] => seqAsJavaList(c)
			case c: Map[_, _] => mapAsJavaMap(c)
			case c: Set[_] => setAsJavaSet(c)
			case _ => super.convertValue(context, value, toType)
		}
	}
}
