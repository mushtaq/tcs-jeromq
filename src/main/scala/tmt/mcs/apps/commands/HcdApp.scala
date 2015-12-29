package tmt.mcs.apps.commands

import caseapp._
import tmt.app.{Assembly, Names, Params}

case class Hcd(params: Params) extends App {
  val updatedParams = params.copy(roles = Names.HcdServer :: params.roles)
  new Assembly(updatedParams).mcsHcdServerSingleton.manager
}

object HcdApp extends AppOf[Hcd] {
  def parser = default
}
