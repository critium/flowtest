import scala.concurrent.duration.DurationInt
import akka.actor.Props.apply
import play.api.Application
import play.api.GlobalSettings
import play.api.Logger
import play.api.Play
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.concurrent.Akka
import akka.actor.Props

import flow.actor.FixerActor

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    runTimer(app)
  }

  def runTimer(app: Application) = {
    Logger.info("Scheduling the timer daemon")
    val fixerActor = Akka.system(app).actorOf(Props(new FixerActor()))
    Akka.system(app).scheduler.schedule(10 seconds, 15 seconds, fixerActor, "fixerDaemon")
  }


}
