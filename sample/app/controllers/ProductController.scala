package controllers

import com.dmitraver.play.thymeleaf.Thymeleaf
import model.Product
import play.api.mvc.{Action, Controller}

object ProductController extends Controller {

	def productList = Action { implicit request =>
		val products = Product.findAll()
		Ok(Thymeleaf.render("product/list", Map("prods" -> products)))
	}
}
