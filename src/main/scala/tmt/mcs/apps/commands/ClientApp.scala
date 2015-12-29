package tmt.mcs.apps.commands

import caseapp._
import tcsstr2.Transition
import tmt.app.{Assembly, Params}

case class Client(params: Params) extends App {

  val mcsHcdClient = new Assembly(params).commandsClient

  Thread.sleep(15000)

  (1 to 5).foreach { _ =>
    mcsHcdClient.lifecycle(Transition.STARTUP)
    Thread.sleep(1000)
  }

}

object ClientApp extends AppOf[Client] {
  def parser = default
}
