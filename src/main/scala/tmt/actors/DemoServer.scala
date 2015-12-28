package tmt.actors

import caseapp._
import tmt.app.{Params, Assembly}

case class DemoServer(params: Params) extends App {
  new Assembly(params).mcsHcdServerSingleton.manager
}

object DemoServerApp extends AppOf[DemoServer] {
  def parser = default
}
