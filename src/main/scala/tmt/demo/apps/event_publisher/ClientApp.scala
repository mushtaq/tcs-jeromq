package tmt.demo.apps.event_publisher

import akka.stream.scaladsl.Source
import caseapp._
import tcsstr2.{Timestamp, tcs_mcs_PositionDemand}
import tmt.app.configs.{Params, Names, Assembly}


import scala.concurrent.duration.DurationInt

case class Client(params: Params) extends App {

  val assembly = new Assembly(params)
  import assembly._

  val demands = Source(-270.0 to 270 by 0.1)
    .zip(Source(-3.0 to 93 by 0.1))
    .zip(Source.tick(10.millis, 10.millis, ()))
    .map { case ((az, el), _) =>
      tcs_mcs_PositionDemand(az, el, Timestamp(System.currentTimeMillis()))
    }

  publisherClient.publish(demands, Names.PositionDemands)

}

object ClientApp extends AppOf[Client] {
  def parser = default
}
