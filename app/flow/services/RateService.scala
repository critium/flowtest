package flow.services

import flow.data._
import flow.data.db._
import flow.data.FixerResponse._

//import dbConfig.driver.api._
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
//import play.api.Play

import scala.util._

case class RateDiff(symbol:String, oldRate:Double, newRate:Double)

object RateService {
  var rates:Map[String, Double] = Map[String, Double]()

  def compare(fixerResponse:FixerResponse):List[RateDiff] = {
    val ratesMap:Map[String, Double] = fixerResponse.rates

    val ratesList:List[RateDiff] = (ratesMap map { case (k:String,v:Double) =>
      val newRate = v
      val oldRate = rates.get(k).getOrElse(v)
      RateDiff(k, oldRate, newRate)
    }).toList

    ratesList.filter(r => r.oldRate != r.newRate)

  }
}
