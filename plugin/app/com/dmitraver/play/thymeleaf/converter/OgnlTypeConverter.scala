package com.dmitraver.play.thymeleaf.converter

import java.util

import ognl.DefaultTypeConverter

import scala.collection.JavaConversions._

class OgnlTypeConverter extends DefaultTypeConverter {
	override def convertValue(context: util.Map[_, _], value: scala.Any, toType: Class[_]): AnyRef = {
		value match {
			case seq: Seq[_] => seqAsJavaList(seq)
			case _ => super.convertValue(context, value, toType)
		}
	}
}
