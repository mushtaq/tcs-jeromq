package tmt.actors

import tcsstr2.Tcs_Command.Msg.TcsMcsLifecycle
import tcsstr2.{command_response, Tcs_Command, Transition}
import tmt.utils.ActorRuntime
import akka.pattern.ask

class McsHcdClient(actorRuntime: ActorRuntime, mcsHcdServerSingleton: McsHcdServerSingleton) {

  import actorRuntime._

  def lifecycle(transition: Transition) = {
    val command = Tcs_Command(TcsMcsLifecycle(transition))
    println(s"***** client sending command: $command")
    (mcsHcdServerSingleton.proxy ? command).mapTo[command_response].onComplete(println)
  }
}
