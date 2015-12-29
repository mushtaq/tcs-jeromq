package tmt.mcs.apps.subscribers

import caseapp._
import tcsstr2.mcs_DriveStatus
import tmt.app.{Assembly, Names, Params}

case class Hcd(params: Params) extends App {
  val assembly = new Assembly(params)
  import assembly._

  subscriberHcd.connect(
    Names.DriveStatus,
    appSettings.mcsDriveStatusPort,
    mcs_DriveStatus
  )
}

object HcdApp extends AppOf[Hcd] {
  def parser = default
}