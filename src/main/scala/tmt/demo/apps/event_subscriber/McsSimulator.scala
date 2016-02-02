package tmt.demo.apps.event_subscriber

import caseapp._
import tcsstr2._
import tmt.app.configs.{Assembly, Params}
import tmt.app.utils.Data

object McsSimulator extends AppOf[McsSimulatorInner] {
  def parser = default
}

case class McsSimulatorInner(params: Params) extends App {

  val assembly = new Assembly(params)
  import assembly._

  val publisher = zmqPublisherFactory.make[mcs_Health](
    appSettings.mcsHealthPort
  )

  publisher
    .publish(Data.healthEventStream)
    .onComplete { x =>
      println(s"completed with value: $x")
      publisher.shutdown()
      runtime.shutdown()
    }
}
