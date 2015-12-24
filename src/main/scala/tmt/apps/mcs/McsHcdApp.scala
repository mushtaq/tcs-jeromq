package tmt.apps.mcs

import tcsstr2.Transition
import tmt.reactivemq.ZmqClient
import tmt.utils.ActorRuntime

object McsHcdApp extends App {

  val runtime = ActorRuntime.create()

  import runtime._

  val zmqClient = new ZmqClient(
    address = "tcp://localhost:5555",
    runtime = runtime
  )

  val mcsClient = new McsClient(zmqClient)

  mcsClient.changeState(Transition.STARTUP).onComplete(x => println(s"result is: $x"))
  mcsClient.changeState(Transition.REBOOT).onComplete(x => println(s"result is: $x"))


  Thread.sleep(100000)
  zmqClient.shutdown()
  runtime.shutdown()

}
