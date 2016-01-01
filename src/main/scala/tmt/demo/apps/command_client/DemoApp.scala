package tmt.demo.apps.command_client

import caseapp._
import tcsstr2.Transition
import tmt.app.configs.{Params, Assembly}

object DemoApp extends AppOf[DemoAppInner] {
  def parser = default
}

case class DemoAppInner(params: Params) extends App {

  val mcsHcdClient = new Assembly(params).commandsClient

  Thread.sleep(15000)

  (1 to 5).foreach { _ =>
    mcsHcdClient.lifecycle(Transition.STARTUP)
    Thread.sleep(1000)
  }

}
