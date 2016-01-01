package tmt.demo.apps.command_client

import caseapp._
import tmt.app.configs.{Params, Names, Assembly}

case class Hcd(params: Params) extends App {
  val updatedParams = params.addRole(Names.HcdServer)
  new Assembly(updatedParams).commandsHcdSingleton.manager
}

object HcdApp extends AppOf[Hcd] {
  def parser = default
}
