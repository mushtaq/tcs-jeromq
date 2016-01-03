package tmt.app.utils

import akka.stream.scaladsl.Source
import tcsstr2.{mcs_DriveStatus, Timestamp, tcs_mcs_PositionDemand}

import scala.concurrent.duration.DurationInt

object Data {

  val demands = Source(-270.0 to 270 by 0.6)
    .zip(Source(-3.0 to 93 by 0.1))
    .zip(Source.tick(10.millis, 10.millis, ()))
    .map { case ((az, el), _) =>
      tcs_mcs_PositionDemand(az, el, Timestamp(System.currentTimeMillis()))
    }

  val driveStatuses = Source
    .tick(10.millis, 10.millis, ())
    .map(_ => currentStatus)


  def currentStatus: mcs_DriveStatus = {
    mcs_DriveStatus(time = Timestamp(System.currentTimeMillis()))
  }

}
