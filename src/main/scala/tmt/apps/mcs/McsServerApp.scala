package tmt.apps.mcs

import tcsstr2.Tcs_Command.Msg.TcsMcsLifecycle
import tcsstr2.command_response.ErrorState
import tcsstr2.{Transition, Tcs_Command, Timestamp, command_response}
import tmt.reactivemq.ZmqServer
import tmt.utils.ActorRuntime

object McsServerApp extends App {

  val runtime = ActorRuntime.create()

  import runtime._

  val server = new ZmqServer(
    address = "tcp://*:5555",
    runtime = runtime
  )

  server.start(Tcs_Command) {
    case Tcs_Command(TcsMcsLifecycle(Transition.STARTUP)) =>
      println("done!!!!")
      command_response(
        ErrorState.OK,
        "",
        Timestamp(System.currentTimeMillis())
      )
    case _                                        =>
      command_response(
        ErrorState.ERROR,
        "something has gone wrong",
        Timestamp(System.currentTimeMillis())
      )

  }.onComplete { x =>
    server.shutdown()
    runtime.shutdown()
  }

}
