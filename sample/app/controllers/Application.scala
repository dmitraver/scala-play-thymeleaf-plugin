package controllers

import java.util.Calendar

import com.github.dmitraver.play.thymeleaf.Thymeleaf
import play.api.mvc._

object Application extends Controller {

  def index = Action { implicit request =>
    Ok(Thymeleaf.render("home", Map("today" -> Calendar.getInstance()))).withSession(request.session + ("user", "John Apricot"))
  }
}