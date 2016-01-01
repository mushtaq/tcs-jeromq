package tmt.demo.apps.event_subscriber

import caseapp._
import tcsstr2.mcs_DriveStatus
import tmt.app.configs.{Params, Names, Assembly}


case class Hcd(params: Params) extends App {
  val assembly = new Assembly(params)
  import assembly._

  subscriberHcd.connect(
    publishingTopic = Names.DriveStatus,
    subscriberPort = appSettings.mcsDriveStatusPort,
    responseParser = mcs_DriveStatus
  )
}

object HcdApp extends AppOf[Hcd] {
  def parser = default
}