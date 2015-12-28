package tmt.apps.mcs

import caseapp._
import tcsstr2.Tcs_Command.Msg.TcsMcsLifecycle
import tcsstr2.command_response.ErrorState
import tcsstr2.{Transition, Tcs_Command, Timestamp, command_response}
import tmt.app.{Params, Assembly}

case class McsServer(params: Params) extends App {

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

object McsServerApp extends AppOf[McsServer] {
  def parser = default
}
