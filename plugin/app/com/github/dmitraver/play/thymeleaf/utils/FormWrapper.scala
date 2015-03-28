package com.github.dmitraver.play.thymeleaf.utils

import play.api.data.Form
import java.util

/**
 * Serves as a wrapper to a standard Play [[play.api.data.Form]] class
 * and provides few helper methods to simplify form access inside a template.
 * It is recommended to always wrap the form in this class before passing it
 * to template.
 * @param form wrapped form
 * @tparam T type class of Form object
 */
class FormWrapper[T] (form: Form[T]) {

	/**
	 * Check whether the form contains errors for the specified field
	 * @param field form field name
	 * @return true if the field contains errors, false otherwise
	 */
	def hasErrors(field: String): Boolean = form.error(field).isDefined

	/**
	 * Return list of errors for the specified field
	 * @param field form field name
	 */
	def errors(field: String) = {
		val errors = new util.LinkedList[String]()
		form.errors(field).foreach(formError => errors add formError.message)
		errors
	}

	/**
	 * Return list of all form errors
	 */
	def allErrors() = {
		val errors = new util.LinkedList[String]()
		form.errors.foreach(formError => errors add formError.message)
		errors
	}

	/**
	 * Return list of all form global errors
	 */
	def globalErrors() = {
		val errors = new util.LinkedList[String]()
		form.globalErrors.foreach(formError => errors add formError.message)
		errors
	}

	/**
	 * Return value for the specified form field
	 * @param field form field name
	 */
	def value(field: String) = {
		form(field).value match {
			case Some(value) => value
			case None => ""
		}
	}
}

object FormWrapper {
	def apply[T](form: Form[T]): FormWrapper[T] = new FormWrapper[T](form)
}
