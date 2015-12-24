package tmt.apps.mcs

import tcsstr2.Tcs_Command.Msg.TcsMcsLifecycle
import tcsstr2.{Tcs_Command, Transition, command_response}
import tmt.reactivemq.ZmqClient

class McsClient(zmqClient: ZmqClient) {

  def lifecycle(transition: Transition) = {
    val command = Tcs_Command(TcsMcsLifecycle(transition))
    zmqClient.query(command, command_response)
  }

}
