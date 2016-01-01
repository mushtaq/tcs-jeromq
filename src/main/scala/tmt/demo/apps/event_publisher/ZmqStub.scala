package tmt.demo.apps.event_publisher

import akka.stream.scaladsl.Sink
import caseapp._
import tcsstr2._

import tmt.app.configs.{Params, Assembly}

object ZmqStub extends AppOf[ZmqStubInner] {
  def parser = default
}

case class ZmqStubInner(params: Params) extends App {

  val assembly = new Assembly(params)
  import assembly._

  val subscriber = zmqSubscriberFactory.make[tcs_mcs_PositionDemand](
    appSettings.mcsPositionDemandPort,
    tcs_mcs_PositionDemand
  )

  subscriber
    .stream
    .runWith(Sink.ignore)
    .onComplete { x =>
      println(s"completed with value: $x")
      subscriber.shutdown()
      runtime.shutdown()
    }
}
