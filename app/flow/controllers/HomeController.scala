package flow.controllers

import flow.services._
import flow.data._
import flow.data.FixerResponse._

import javax.inject._

import play.api.libs.json._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Returns the FixerResponse
   * {
   * "base"  : // the base currency
   * "date"  : // the requested date of the request
   * "rates" : [{
   *    // the key value pair given the target currency and the conversion from the base currency
   * }]
   * }
   */
  def getRates(
    base:Option[String],
    target:Option[String],
    timestamp:Option[String]
  ) = Action { implicit request =>
    val fixerBody:FixerResponse = FixerService.callFixer(base, target, timestamp)
    Ok(JsonResponse(200, Json.toJson(fixerBody)))
  }
}
