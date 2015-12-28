package tmt.actors

import akka.cluster.pubsub.DistributedPubSub
import akka.pattern.ask
import tcsstr2.Tcs_Command.Msg.TcsMcsLifecycle
import tcsstr2.{command_response, Tcs_Command, Transition}
import tmt.utils.ActorRuntime

class McdHcdClient(actorRuntime: ActorRuntime) {

  import actorRuntime._

  def lifecycle(transition: Transition) = {
    val command = Tcs_Command(TcsMcsLifecycle(transition))
    println(s"sending command: $command")
    (DistributedPubSub(system).mediator ? command).mapTo[command_response]
  }
}
