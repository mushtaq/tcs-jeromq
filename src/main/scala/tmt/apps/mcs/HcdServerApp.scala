package tmt.apps.mcs

import caseapp._
import tmt.app.{Names, Assembly, Params}

case class HcdServer(params: Params) extends App {
  val updatedParams = params.copy(roles = Names.HcdServer :: params.roles)
  new Assembly(updatedParams).mcsHcdServerSingleton.manager
}

object HcdServerApp extends AppOf[HcdServer] {
  def parser = default
}
