package tmt.demo.apps.event_publisher

import akka.stream.scaladsl.Source
import caseapp._
import tmt.app.configs.{Assembly, Names, Params}
import tmt.app.utils.Data

object DemoApp extends AppOf[DemoAppInner] {
  def parser = default
}

case class DemoAppInner(params: Params) extends App {

  val assembly = new Assembly(params)
  import assembly._

  val continuousDemands = Source.repeat(()).flatMapConcat(_ => Data.demands)

  eventPublisher.publish(continuousDemands, Names.PositionDemands)

}
