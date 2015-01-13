package com.dmitraver.play.thymeleaf.utils

import play.api.data.Form
import java.util

class FormWrapper[T] (form: Form[T]) {

	def hasErrors(field: String): Boolean = form.error(field).isDefined

	def errors(field: String) = {
		val errors = new util.LinkedList[String]()
		form.errors(field).foreach(formError => errors add formError.message)
		errors
	}

	def allErrors() = {
		val errors = new util.LinkedList[String]()
		form.errors.foreach(formError => errors add formError.message)
		errors
	}

	def globalErrors() = {
		val errors = new util.LinkedList[String]()
		form.globalErrors.foreach(formError => errors add formError.message)
		errors
	}
}

object FormWrapper {
	def apply[T](form: Form[T]): FormWrapper[T] = new FormWrapper[T](form)
}
