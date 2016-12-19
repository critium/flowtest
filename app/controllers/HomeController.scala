package controllers

import flow.services._

import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def getRates(
    base:Option[String],
    target:Option[String],
    timestamp:Option[String]
  ) = Action { implicit request =>
    FixerService.callFixer(base, target, timestamp)
    Ok("hi" + base + target + timestamp)
  }
}
