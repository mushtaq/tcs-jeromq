package tmt.apps.mcs

import tcsstr2.Transition
import tmt.app.{Params, Assembly}
import caseapp._

case class McsHcd(params: Params) extends App {

  val assembly = new Assembly(params)
  import assembly._

  mcsClient.lifecycle(Transition.STARTUP).onComplete(x => println(s"result is: $x"))
  mcsClient.lifecycle(Transition.REBOOT).onComplete(x => println(s"result is: $x"))

  Thread.sleep(100000)
  zmqClient.shutdown()
  runtime.shutdown()
}

object McsHcdApp extends AppOf[McsHcd] {
  def parser = default
}
