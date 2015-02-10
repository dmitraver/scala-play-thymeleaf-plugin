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
		        "age" -> number.verifying("Age must be between 10 and 100 years", age => age >= 10 && age <= 100))(User.apply)(User.unapply)
	)

	def register() = Action { implicit request =>
		Ok(Thymeleaf.render("register", Map("userForm" -> FormWrapper(registrationForm))))
	}

	def userProfile() = Action { implicit request =>
		val form = registrationForm.bindFromRequest

		form.fold(
			formWithErrors => BadRequest(Thymeleaf.render("register", Map("userForm" -> FormWrapper(formWithErrors)))),
		  user => Ok(Thymeleaf.render("userprofile", Map("user" -> user)))
		)
	}

}
