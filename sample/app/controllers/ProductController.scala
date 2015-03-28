package controllers

import com.github.dmitraver.play.thymeleaf.Thymeleaf
import model.Product
import play.api.mvc.{Action, Controller}

object ProductController extends Controller {

	def productList = Action { implicit request =>
		val products = Product.findAll()
		Ok(Thymeleaf.render("product/list", Map("prods" -> products)))
	}
	
	def commentsList(prodId: Int) = Action { implicit request => 
		val product = Product.findById(prodId)
		product match {
			case Some(x) => Ok(Thymeleaf.render("product/comments", Map("prod" -> x)))
			case None => Ok("Product with id = " + prodId + " is not found")
		}
	}
}
