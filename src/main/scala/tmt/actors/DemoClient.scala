package tmt.actors

import caseapp._
import tcsstr2.Transition
import tmt.app.{Assembly, Params}

case class DemoClient(params: Params) extends App {

  val mcsHcdClient = new Assembly(params).mcsHcdClient

  Thread.sleep(1000)
  mcsHcdClient.lifecycle(Transition.STARTUP)
  Thread.sleep(1000)
  mcsHcdClient.lifecycle(Transition.STARTUP)
  Thread.sleep(1000)
  mcsHcdClient.lifecycle(Transition.STARTUP)
  Thread.sleep(1000)
  mcsHcdClient.lifecycle(Transition.STARTUP)
}

object DemoClientApp extends AppOf[DemoClient] {
  def parser = default
}
