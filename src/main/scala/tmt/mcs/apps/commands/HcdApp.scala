package tmt.mcs.apps.commands

import caseapp._
import tmt.app.{Assembly, Names, Params}

case class Hcd(params: Params) extends App {
  val updatedParams = params.addRole(Names.HcdServer)
  new Assembly(updatedParams).commandsHcdSingleton.manager
}

object HcdApp extends AppOf[Hcd] {
  def parser = default
}
