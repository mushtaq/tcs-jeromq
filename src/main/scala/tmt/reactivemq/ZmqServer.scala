package tmt.reactivemq

import com.trueaccord.scalapb.{GeneratedMessage, GeneratedMessageCompanion}
import org.zeromq.ZMQ
import sample.ErrorMsg
import tmt.utils.{ActorRuntime, EC, PbMessage}

import scala.concurrent.ExecutionContext.global
import scala.concurrent.Future
import scala.util.control.NonFatal

class ZmqServer(address: String, runtime: ActorRuntime) {

  import runtime._

  private val socket = zmqContext.socket(ZMQ.REP)
  socket.bind(address)
  private val ec = EC.singleThreadedEc()

  def start[Req <: PbMessage.Of[Req]](
    reqParser: GeneratedMessageCompanion[Req]
  )(f: Req => GeneratedMessage): Future[Unit] = Future {
    while (true) {
      try {
        val message = socket.recv(0)
        val request = reqParser.parseFrom(message)
        println(s"Received : [$request]")
        Thread.sleep(100)
        socket.send(f(request).toByteArray, 0)
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
