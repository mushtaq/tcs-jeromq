package tmt.app

import tmt.utils.ActorRuntime

class AppSettings(actorRuntime: ActorRuntime) {

  import actorRuntime._

  val config = system.settings.config

  val mcsIp = config.getString("mcs.ip")
  val mcsCommandsPort = config.getString("mcs.ports.commands")
}
