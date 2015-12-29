package tmt.mcs.clients

import akka.pattern.ask
import tcsstr2.Tcs_Command.Msg.TcsMcsLifecycle
import tcsstr2.{Tcs_Command, Transition, command_response}
import tmt.mcs.hcd.CommandsHcdSingleton
import tmt.utils.ActorRuntime

class CommandsClient(actorRuntime: ActorRuntime, mcsHcdServerSingleton: CommandsHcdSingleton) {

  import actorRuntime._

  def lifecycle(transition: Transition) = {
    val command = Tcs_Command(TcsMcsLifecycle(transition))
    println(s"***** client sending command: $command")
    (mcsHcdServerSingleton.proxy ? command).mapTo[command_response].onComplete(println)
  }
}
