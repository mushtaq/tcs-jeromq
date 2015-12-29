package tmt.mcs.apps.publishers

import akka.stream.scaladsl.Sink
import caseapp._
import tcsstr2._
import tmt.app.{Assembly, Params}

case class Zmq(params: Params) extends App {

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

object ZmqApp extends AppOf[Zmq] {
  def parser = default
}
