package flow.data

import play.api.mvc.Codec
import play.api.http._
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class JsonResponse(code:Int, data:JsValue, ts:java.util.Date = new java.util.Date())

object JsonResponse {
  val fmt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")

  def jsonTpl(r:JsonResponse) = {
    (r.code, r.data, fmt.format(r.ts))
  }

  /**
   *
   */
  implicit val jsonResponseWrites: Writes[JsonResponse] = (
    (JsPath \ "code").write[Int] and
    (JsPath \ "data").write[JsValue] and
    (JsPath \ "ts").write[String]
  )(jsonTpl _)

  implicit def contentTypeOf_JsonResponse(implicit codec: Codec): ContentTypeOf[JsonResponse] = {
    ContentTypeOf[JsonResponse](Some(ContentTypes.JSON))
  }

  implicit def writeableOf_JsonResponse(implicit codec: Codec): Writeable[JsonResponse] = {
    Writeable(jsval => akka.util.ByteString.fromString(Json.stringify(Json.toJson(jsval)), "UTF-8"))
  }
}