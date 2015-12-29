package tmt.mcs.apps.subscribers

import akka.stream.scaladsl.Source
import caseapp._
import tcsstr2._
import tmt.app.{Assembly, Params}

import scala.concurrent.duration._

case class Zmq(params: Params) extends App {

  val assembly = new Assembly(params)
  import assembly._

  val publisher = zmqPublisherFactory.make[mcs_DriveStatus](
    appSettings.mcsDriveStatusPort
  )

  val driveStatuses = Source
    .tick(10.millis, 10.millis, ())
    .map(_ => mcs_DriveStatus(time = Timestamp(System.currentTimeMillis())))


  publisher
    .publish(driveStatuses)
    .onComplete { x =>
      println(s"completed with value: $x")
      publisher.shutdown()
      runtime.shutdown()
    }
}

object ZmqApp extends AppOf[Zmq] {
  def parser = default
}
