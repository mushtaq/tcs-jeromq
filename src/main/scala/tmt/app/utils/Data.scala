package tmt.app.utils

import akka.stream.scaladsl.Source
import tcsstr2.mcs_Health.Health
import tcsstr2._

import scala.concurrent.duration.DurationInt

object Data {

  val demandStream = Source(-270.0 to 270 by 0.6)
    .zip(Source(-3.0 to 93 by 0.1))
    .zip(Source.tick(10.millis, 10.millis, ()))
    .map { case ((az, el), _) =>
      tcs_mcs_PositionDemand(az, el, Timestamp(System.currentTimeMillis()))
    }

  val healthEventStream = Source
    .tick(1.second, 1.second, ())
    .map(_ => currentHealth)


  def currentHealth = {
    mcs_Health(
      health = Health.GOOD,
      simulated = Bool.FALSE,
      time = Timestamp(System.currentTimeMillis())
    )
  }

}
