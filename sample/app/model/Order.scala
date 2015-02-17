package model

import java.util.Calendar

import utils.CalendarUtil

case class Order(id: Int, date: Calendar, customer: Customer, orderLines: Set[OrderLine] = Set[OrderLine]())

object Order {

	private val customerOne = Customer.findById(1).get
	private val customerFour = Customer.findById(4).get
	private val customerSix = Customer.findById(6).get

	private val productOne = Product.findById(1).get
	private val productTwo = Product.findById(2).get
	private val productThree = Product.findById(3).get
	private val productFour = Product.findById(4).get

	private val orderOne = Order(1, CalendarUtil.calendarFor(2009, 1, 12, 10, 23), customerFour,
		Set(OrderLine(productTwo, 2, 0.99), OrderLine(productThree, 4, 2.50), OrderLine(productFour, 1, 15.50)))

	private val orderTwo = Order(2, CalendarUtil.calendarFor(2010, 6, 9, 21, 1), customerSix,
		Set(OrderLine(productOne, 5, 3.75), OrderLine(productFour, 2, 17.99)))

	private val orderThree = Order(3, CalendarUtil.calendarFor(2010, 7, 18, 22, 32), customerOne,
		Set(OrderLine(productOne, 8, 5.99)))

	private val ordersById = Map(1 -> orderOne, 2 -> orderTwo, 3 -> orderThree)
	private val ordersByCustomerId = Map(customerFour.id -> List(orderOne), customerSix.id -> List(orderTwo), customerFour.id -> List(orderThree))

	def findAll(): List[Order] = ordersById.values.toList

	def findById(id: Int): Option[Order] = ordersById.get(id)

	def findByCustomerId(id: Int): List[Order] = ordersByCustomerId.getOrElse(id, List[Order]())
}
