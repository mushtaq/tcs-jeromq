package tmt.apps.mcs

import caseapp._
import tcsstr2.Transition
import tmt.app.{Assembly, Params}

case class HcdClient(params: Params) extends App {

  val mcsHcdClient = new Assembly(params).mcsHcdClient

  Thread.sleep(15000)
  mcsHcdClient.lifecycle(Transition.STARTUP)
  Thread.sleep(1000)
  mcsHcdClient.lifecycle(Transition.STARTUP)
  Thread.sleep(1000)
  mcsHcdClient.lifecycle(Transition.STARTUP)
  Thread.sleep(1000)
  mcsHcdClient.lifecycle(Transition.STARTUP)
}

object HcdClientApp extends AppOf[HcdClient] {
  def parser = default
}
