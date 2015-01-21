package controllers

import com.dmitraver.play.thymeleaf.Thymeleaf
import com.dmitraver.play.thymeleaf.utils.FormWrapper
import model.User
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}

object RegistrationController extends Controller {

	private val registrationForm = Form[User] (
		mapping("firstName" -> nonEmptyText,
		        "lastName" -> nonEmptyText,
		        "age" -> number(min = 10, max = 100))(User.apply)(User.unapply)
	)

	def register() = Action { implicit request =>
		Ok(Thymeleaf.render("register", Map("userForm" -> FormWrapper(registrationForm))))
	}
}
