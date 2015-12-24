package tmt.reactivemq

import com.trueaccord.scalapb.{GeneratedMessageCompanion, GeneratedMessage}
import org.zeromq.ZMQ
import sample.{ErrorMsg, Command}
import tmt.utils.{PbMessage, EC, ActorRuntime}

import scala.concurrent.ExecutionContext.global
import scala.concurrent.Future
import scala.util.control.NonFatal

class ZmqServer(address: String, runtime: ActorRuntime) {

  import runtime._

  private val socket = zmqContext.socket(ZMQ.REP)
  socket.bind(address)
  private val ec = EC.singleThreadedEc()

  def start[Cmd <: PbMessage.Of[Cmd]](
    cmdParser: GeneratedMessageCompanion[Cmd]
  )(f: Cmd => GeneratedMessage): Future[Unit] = Future {
    while (true) {
      try {
        val message = socket.recv(0)
        val msg = cmdParser.parseFrom(message)
        println(s"Received : [$msg]")
        Thread.sleep(100)
        socket.send(f(msg).toByteArray, 0)
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
