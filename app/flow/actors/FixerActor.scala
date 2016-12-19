package flow.actor

import flow.services._
import akka.actor._

object FixerActor {
  def props = Props[FixerActor]
}

class FixerActor extends Actor {
  import FixerActor._

  def receive = {
    case _ =>
      println("HI from the actor")
      val latest = FixerService.getLatest
      val diff = RateService.compare(latest)

      if(diff.size > 0) {
        WebhookService.notify(diff)
      }
  }
}
