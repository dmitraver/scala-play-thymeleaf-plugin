package controllers

import com.github.dmitraver.play.thymeleaf.Thymeleaf
import model.Order
import play.api.mvc.{Action, Controller}

object OrderController extends Controller {

	def ordersList() = Action { implicit request =>
		val orders = Order.findAll()
		Ok(Thymeleaf.render("order/list", Map("orders" -> orders)))
	}

	def details(orderId: Int) = Action { implicit request =>
		val order = Order.findById(orderId)
		order match {
			case Some(o) => Ok(Thymeleaf.render("order/details", Map("order" -> o)))
			case None => Ok("Order with id = "+ orderId + " is not found")
		}
	}
}
