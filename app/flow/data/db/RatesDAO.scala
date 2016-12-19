package flow.data.db

import flow.data._

import scala.util._
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import java.sql.Timestamp

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

class Rates(tag:Tag) extends Table[Rate](tag, "rate") {
  def * = (id, symbol, value, ts) <> (Rate.tupled, Rate.unapply _)

  def id = column[Long]("id", O.PrimaryKey)
  def symbol = column[String]("symbol")
  def value = column[Double]("value")
  def ts = column[java.sql.Timestamp]("ts")
}

object RateDAO {
  //import driver.api._
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val db = dbConfig.db

  val ratesDB = TableQuery[Rates]

  def save(rates:List[Rate]):Try[Boolean] = {
    Try {
      //println("saving rates: " + rates.size)
      rates.map { r =>
        db.run(ratesDB += r).map { _ => () }
      }
      true
    }
  }

  /**
   * timestamps are in batches, so for get the last 2 latest timestamps
   * and then get where they are different
   */
  def getDiff = {
    //val q1 = ratesDB.result
    //for {
      //r <- db.run(q1)
    //} yield {
      //println(r.mkString(","))
    //}

    //val qTS = (for {
      //rates <- ratesDB.result
      //ts <- DBIO.sequence(
        //rates
          //.groupBy(_.ts)
          //.toSeq
          //.map{
            //case (ts, rates) => DBIO.successful(ts)
          //}
          //.take(2)
      //)
    //} yield (ts))
    //val res = db.run(qTS)
    //println(res)
    //Unit
    val rawQ = """
    (select distinct (ts) from rate order by ts desc) as tsList
    """
  }

}
