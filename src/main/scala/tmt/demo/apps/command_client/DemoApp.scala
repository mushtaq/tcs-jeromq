package tmt.demo.apps.command_client

import caseapp._
import tcsstr2.Transition
import tmt.app.configs.{Params, Assembly}

object DemoApp extends AppOf[DemoAppInner] {
  def parser = default
}

case class DemoAppInner(params: Params) extends App {

  val assembly = new Assembly(params)
  import assembly._

  Iterator.from(1).foreach { _ =>
    commandsClient.lifecycle(Transition.STARTUP).onComplete(println)
    Thread.sleep(2000)
  }

}
