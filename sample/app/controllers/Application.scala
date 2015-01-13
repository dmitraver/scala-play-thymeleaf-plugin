package controllers

import com.dmitraver.play.thymeleaf.Thymeleaf
import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action { implicit request =>
    Ok(Thymeleaf.render("index"))
  }

}