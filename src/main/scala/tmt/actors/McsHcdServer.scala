package tmt.actors

import akka.actor.Actor
import tcsstr2.{command_response, Tcs_Command}
import tmt.reactivemq.ZmqClient
import akka.pattern.pipe

class McsHcdServer(zmqClient: ZmqClient) extends Actor {

  import context.dispatcher

  def receive = {
    case command: Tcs_Command =>
      val result = zmqClient.query(command, command_response)
      result pipeTo sender()
  }
}
