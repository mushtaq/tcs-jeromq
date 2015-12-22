package tmt.reactivemq

import com.trueaccord.scalapb.GeneratedMessage
import org.zeromq.ZMQ
import sample.{ErrorMsg, Command}
import tmt.utils.{EC, ActorRuntime}

import scala.concurrent.ExecutionContext.global
import scala.concurrent.Future
import scala.util.control.NonFatal

class ZmqServer(address: String, runtime: ActorRuntime) {

  import runtime._

  private val socket = zmqContext.socket(ZMQ.REP)
  socket.bind(address)
  private val ec = EC.singleThreadedEc()

  def start(f: Command.Msg => GeneratedMessage): Future[Unit] = Future {
    while (true) {
      try {
        val message = socket.recv(0)
        val command = Command.parseFrom(message)
        println(s"Received : [$command]")
        Thread.sleep(100)
        socket.send(f(command.msg).toByteArray, 0)
      } catch {
        case NonFatal(ex) =>
          ex.printStackTrace()
          socket.send(ErrorMsg("11").toByteArray, 0)
      }

    }
  }(ec).map { x =>
    println(s"completed with value: $x")
  }(global)

  def shutdown(): Unit = {
    socket.close()
    ec.shutdown()
  }
}
