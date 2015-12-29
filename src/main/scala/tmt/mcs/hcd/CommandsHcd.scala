package tmt.mcs.hcd

import akka.actor.Actor
import akka.pattern.pipe
import tcsstr2.{Tcs_Command, command_response}
import tmt.mcs.reactivemq.ZmqClient

class CommandsHcd(zmqClient: ZmqClient) extends Actor {

  import context.dispatcher

  def receive = {
    case command: Tcs_Command =>
      println(s"***** mcs hcd server received $command from ${sender()}")
      val result = zmqClient.query(command, command_response)
      result pipeTo sender()
  }
}
