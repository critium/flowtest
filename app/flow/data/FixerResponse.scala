package flow.data

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class FixerResponse(base:String, date:String, rates:Map[String, Double])

object FixerResponse {
  implicit val fixerReads: Reads[FixerResponse] = (
    (JsPath \ "base").read[String] and
    (JsPath \ "date").read[String] and
    (JsPath \ "rates").read[Map[String, Double]]
  )(FixerResponse.apply _)

  implicit val fixerWrites: Writes[FixerResponse] = (
    (JsPath \ "base").write[String] and
    (JsPath \ "timestamp").write[String] and
    (JsPath \ "rates").write[Map[String, Double]]
  )(unlift(FixerResponse.unapply))
}
