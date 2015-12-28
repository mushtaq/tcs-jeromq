package tmt.apps.mcs

import caseapp._
import tmt.app.{Assembly, Params}

case class HcdServer(params: Params) extends App {
  new Assembly(params).mcsHcdServerSingleton.manager
}

object HcdServerApp extends AppOf[HcdServer] {
  def parser = default
}
