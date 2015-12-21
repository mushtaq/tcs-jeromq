package tmt.reactivemq

import com.trueaccord.scalapb.{GeneratedMessage, GeneratedMessageCompanion}
import org.zeromq.ZMQ
import org.zeromq.ZMQ.Context
import scala.concurrent.ExecutionContext.global
import scala.concurrent.Future

class ZmqServer(context: Context, address: String) {
  val socket = context.socket(ZMQ.REP)
  socket.bind(address)
  val ec = EC.singleThreadedEc()

  def start[Req <: PbMessage.Of[Req]](
    requestParser: GeneratedMessageCompanion[Req]
  )(
    f: Req => GeneratedMessage
  ): Future[Unit] = Future {
    while (true) {
      val message = socket.recv(0)
      val request = requestParser.parseFrom(message)
      println(s"Received : [$request]")
      Thread.sleep(100)
      socket.send(f(request).toByteArray, 0)
    }
  }(ec).map { x =>
    println(s"completed with value: $x")
    socket.close()
  }(global)

  def stop(): Unit = {
    socket.close()
  }
}
