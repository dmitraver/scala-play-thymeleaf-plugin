package model

import java.util.Calendar

import utils.CalendarUtil

case class Customer(id: Int, name: String, customerSince: Calendar) {
	def getId = id
	def getName = name
	def getCustomerSince = customerSince
}

object Customer {

	private val customers = Map(1 -> Customer(1, "James Cucumber", CalendarUtil.calendarFor(2006, 4, 2, 13, 20)),
															2 -> Customer(2, "Anna Lettuce", CalendarUtil.calendarFor(2005, 1, 30, 17, 14)),
	                            3 -> Customer(3, "Boris Tomato", CalendarUtil.calendarFor(2008, 12, 2, 9, 53)),
	                            4 -> Customer(4, "Shannon Parsley", CalendarUtil.calendarFor(2009, 3, 24, 10, 45)),
	                            5 -> Customer(5, "Susan Cheddar", CalendarUtil.calendarFor(2007, 10, 1, 15, 2)),
	                            6 -> Customer(6, "George Garlic", CalendarUtil.calendarFor(2010, 5, 18, 20, 30)))

	def findAll(): List[Customer] = {
		customers.values.toList
	}

	def findById(id: Int): Option[Customer] = {
		customers.get(id)
	}
}
