package tmt.demo.hcd_drivers

import akka.pattern.ask
import tcsstr2.Tcs_Command.Msg.TcsMcsLifecycle
import tcsstr2.{Tcs_Command, Transition, command_response}
import tmt.demo.hcds.CommandHcdSingleton
import tmt.app.utils.ActorRuntime

class CommandClient(actorRuntime: ActorRuntime, mcsHcdServerSingleton: CommandHcdSingleton) {

  import actorRuntime._

  def lifecycle(transition: Transition) = {
    val command = Tcs_Command(TcsMcsLifecycle(transition))
    println(s"***** client sending command: $command")
    (mcsHcdServerSingleton.proxy ? command).mapTo[command_response].onComplete(println)
  }
}
