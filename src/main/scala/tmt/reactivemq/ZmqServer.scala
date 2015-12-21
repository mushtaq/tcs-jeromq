package tmt.reactivemq

import com.trueaccord.scalapb.{GeneratedMessage, GeneratedMessageCompanion}
import org.zeromq.ZMQ
import scala.concurrent.ExecutionContext.global
import scala.concurrent.Future

class ZmqServer[Req <: PbMessage.Of[Req], Res <: GeneratedMessage](
  address: String,
  requestParser: GeneratedMessageCompanion[Req],
  actorConfigs: ActorConfigs
) {

  import actorConfigs._

  private val socket = zmqContext.socket(ZMQ.REP)
  socket.bind(address)
  private val ec = EC.singleThreadedEc()

  def start(f: Req => Res): Future[Unit] = Future {
    while (true) {
      val message = socket.recv(0)
      val request = requestParser.parseFrom(message)
      println(s"Received : [$request]")
      Thread.sleep(100)
      socket.send(f(request).toByteArray, 0)
    }
  }(ec).map { x =>
    println(s"completed with value: $x")
  }(global)

  def stop(): Unit = {
    socket.close()
    ec.shutdown()
  }
}
