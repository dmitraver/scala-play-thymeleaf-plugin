package com.dmitraver.play.thymeleaf.l18n

import org.thymeleaf.Arguments
import org.thymeleaf.messageresolver.{MessageResolution, IMessageResolver}
import play.api.i18n.{Messages, Lang}

class PlayMessageResolver extends IMessageResolver {

	private val MESSAGE_RESOLVER_NAME = "PLAY_MESSAGE_RESOLVER"
	private val MESSAGE_RESOLVER_ORDER = 0
	private var language: Lang = Lang("en")

	def setLanguage(language: Lang) = {
		this.language = language
	}

	override def getName: String = MESSAGE_RESOLVER_NAME

	override def resolveMessage(arguments: Arguments, key: String, messageParameters: Array[AnyRef]): MessageResolution = {
		new MessageResolution(Messages(key, messageParameters: _*))
	}

	override def initialize(): Unit = {}

	override def getOrder: Integer = MESSAGE_RESOLVER_ORDER
}
