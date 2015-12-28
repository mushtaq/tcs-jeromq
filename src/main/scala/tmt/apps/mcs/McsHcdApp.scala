package tmt.apps.mcs

import tcsstr2.Transition
import tmt.app.Assembly

object McsHcdApp extends App {

  val assembly = new Assembly("dev", None)
  import assembly._

  mcsClient.lifecycle(Transition.STARTUP).onComplete(x => println(s"result is: $x"))
  mcsClient.lifecycle(Transition.REBOOT).onComplete(x => println(s"result is: $x"))

  Thread.sleep(100000)
  zmqClient.shutdown()
  runtime.shutdown()
}
