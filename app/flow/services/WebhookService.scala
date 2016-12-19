package flow.services

import flow.data._
import flow.data.db._
import flow.data.FixerResponse._

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.util._

import scalaj.http._

object WebhookService {
  val registry = List("http://localhost:7091/webhooks")
  def notify(diffs:List[RateDiff]):Unit ={
    registry.map { r =>
      val response:HttpResponse[String] = Http(r).asString

      // handle response code 200 and any other else
      println(s"Got ${response.body} from $r")
    }
  }
}
