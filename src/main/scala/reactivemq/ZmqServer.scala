package reactivemq

import com.trueaccord.scalapb.{GeneratedMessage, GeneratedMessageCompanion}
import org.zeromq.ZMQ
import org.zeromq.ZMQ.Context

class ZmqServer(context: Context, address: String) {
  val socket = context.socket(ZMQ.REP)
  socket.bind(address)

  def start[Req <: PbMessage.Of[Req]](
    requestParser: GeneratedMessageCompanion[Req]
  )(
    f: Req => GeneratedMessage
  ): Unit = {
    while (true) {
      val message = socket.recv(0)
      val request = requestParser.parseFrom(message)
      println(s"Received : [$request]")
      Thread.sleep(1000)
      socket.send(f(request).toByteArray, 0)
    }
  }

  def stop(): Unit = {
    socket.close()
  }
}
