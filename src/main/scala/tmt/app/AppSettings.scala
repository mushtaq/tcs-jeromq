package tmt.app

import tmt.utils.ActorRuntime

class AppSettings(actorRuntime: ActorRuntime) {

  import actorRuntime._

  val config = system.settings.config

  val mcsIp = config.getString("mcs.ip")

  val mcsCommandsPort = config.getInt("mcs.ports.commands")

  val mcsPositionDemandPort = config.getInt("mcs.ports.subscribers.position_demand")

  val mcsDriveStatusPort = config.getInt("mcs.ports.publishers.drive_status")
}
