package tmt.demo.apps.event_publisher

import caseapp._
import tcsstr2.tcs_mcs_PositionDemand
import tmt.app.configs.{Params, Names, Assembly}


case class Hcd(params: Params) extends App {
  val assembly = new Assembly(params)
  import assembly._

  publisherHcd.connect[tcs_mcs_PositionDemand](
    subscriberTopic = Names.PositionDemands,
    publishingPort = appSettings.mcsPositionDemandPort
  )
}

object HcdApp extends AppOf[Hcd] {
  def parser = default
}
