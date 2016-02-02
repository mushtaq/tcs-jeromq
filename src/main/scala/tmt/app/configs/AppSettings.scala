package tmt.app.configs

import tmt.app.utils.ActorRuntime

class AppSettings(actorRuntime: ActorRuntime) {

  import actorRuntime._

  val config = system.settings.config

  val mcsHostname = config.getString("mcs.hostname")

  val mcsCommandsPort = config.getInt("mcs.ports.commands")

  val mcsPositionDemandPort = config.getInt("mcs.ports.subscribers.position_demand")

  val mcsHealthPort = config.getInt("mcs.ports.publishers.health")
}
