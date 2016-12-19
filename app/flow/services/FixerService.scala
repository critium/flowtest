package flow.services

import scalaj.http._
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class FixerResponse(base:String, date:String, rates:Map[String, Double])


object FixerService {
  val rootURL = "http://api.fixer.io"
  val baseQ = "base";
  val symbolsQ = "symbols";

  implicit val locationReads: Reads[FixerResponse] = (
    (JsPath \ "base").read[String] and
    (JsPath \ "date").read[String] and
    (JsPath \ "rates").read[Map[String, Double]]
  )(FixerResponse.apply _)

  def date(timestamp:Option[String]) = timestamp match {
    case Some(ts) => ts
    case _ => "latest"
  }

  /**
   * append the query parameters to the http request
   */
  def buildParams(request:HttpRequest, base:Option[String], target:Option[String]):HttpRequest = {
    (base, target) match {
      case (Some(baseValue), Some(targetValue)) =>
        request
          .param(baseQ, baseValue)
          .param(symbolsQ, targetValue)
      case (Some(baseValue), None) =>
        request
          .param(baseQ, baseValue)
      case (None, Some(targetValue)) =>
        request
          .param(symbolsQ, targetValue)
    }
  }


  /**
   * Calls the fixer api and parses the json
   */
  def callFixer(base:Option[String], target:Option[String], timestamp:Option[String]):Unit = {
    val request:HttpRequest = Http(s"${rootURL}/${date(timestamp)}")
    val response:HttpResponse[String] = buildParams(request, base, target).asString

    val fixerResponse:FixerResponse = Json.parse(response.body).as[FixerResponse]

    println("Y: " + response.body + " " + response.code + " " + fixerResponse)
  }
}
