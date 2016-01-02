package tmt.demo.apps.event_subscriber

import akka.stream.scaladsl.Source
import caseapp._
import tcsstr2._

import tmt.app.configs.{Params, Assembly}

import scala.concurrent.duration._

object ZmqStub extends AppOf[ZmqStubInner] {
  def parser = default
}

case class ZmqStubInner(params: Params) extends App {

  val assembly = new Assembly(params)
  import assembly._

  val publisher = zmqPublisherFactory.make[mcs_DriveStatus](
    appSettings.mcsDriveStatusPort
  )

  val driveStatuses = Source
    .tick(10.millis, 10.millis, ())
    .map(_ => currentStatus)


  def currentStatus: mcs_DriveStatus = {
    mcs_DriveStatus(time = Timestamp(System.currentTimeMillis()))
  }

  publisher
    .publish(driveStatuses)
    .onComplete { x =>
      println(s"completed with value: $x")
      publisher.shutdown()
      runtime.shutdown()
    }
}
