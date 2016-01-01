package tmt.demo.apps

import caseapp._
import tcsstr2.{mcs_DriveStatus, tcs_mcs_PositionDemand}
import tmt.app.configs.{Params, Names, Assembly}

object HcdService extends AppOf[HcdServiceInner] {
  def parser = default
}

case class HcdServiceInner(params: Params) extends App {
  val updatedParams = params.addRole(Names.HcdServer)

  val assembly = new Assembly(updatedParams)
  import assembly._

  //start command server hcd
  commandServerHcdSingleton.manager

  //start event subscriber hcd
  eventSubscriberHcd.connect[tcs_mcs_PositionDemand](
    subscriberTopic = Names.PositionDemands,
    publishingPort = appSettings.mcsPositionDemandPort
  )

  //start event publishing hcd
  eventPublisherHcd.connect(
    publishingTopic = Names.DriveStatus,
    subscriberPort = appSettings.mcsDriveStatusPort,
    responseParser = mcs_DriveStatus
  )

}
