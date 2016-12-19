package flow.services

import flow.data._
import flow.data.FixerResponse._

import scalaj.http._
import play.api.libs.json._
import play.api.libs.functional.syntax._

object FixerService {
  val rootURL = "http://api.fixer.io"
  val baseQ = "base";
  val symbolsQ = "symbols";

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
      case (None, None) =>
        request
    }
  }


  /**
   * Calls the fixer api and parses the json
   */
  def callFixer(base:Option[String], target:Option[String], timestamp:Option[String]):FixerResponse = {
    val request:HttpRequest = Http(s"${rootURL}/${date(timestamp)}")
    val response:HttpResponse[String] = buildParams(request, base, target).asString

    val fixerResponse:FixerResponse = Json.parse(response.body).as[FixerResponse]

    //println("Y: " + response.body + " " + response.code + " " + fixerResponse)

    fixerResponse
  }

  def getLatest:FixerResponse = {
    callFixer(None, None, None)
  }
}
