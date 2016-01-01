package tmt.demo.apps.command_client

import caseapp._
import tcsstr2.Tcs_Command.Msg.TcsMcsLifecycle
import tcsstr2.command_response.ErrorState
import tcsstr2.{Tcs_Command, Timestamp, Transition, command_response}
import tmt.app.configs.{Params, Assembly}

case class Zmq(params: Params) extends App {

  val assembly = new Assembly(params)
  import assembly._

  zmqServer.start(Tcs_Command) { command =>
    command.msg match {
      case TcsMcsLifecycle(Transition.STARTUP) =>
        println("done!!!!")
        command_response(
          ErrorState.OK,
          "",
          Timestamp(System.currentTimeMillis())
        )
      case _                                   =>
        command_response(
          ErrorState.ERROR,
          "something has gone wrong",
          Timestamp(System.currentTimeMillis())
        )
    }
  }.onComplete { x =>
    zmqServer.shutdown()
    runtime.shutdown()
  }

}

object ZmqApp extends AppOf[Zmq] {
  def parser = default
}