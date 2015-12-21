package reactivemq

import com.trueaccord.scalapb.{GeneratedMessageCompanion, Message, GeneratedMessage}
import org.zeromq.ZMQ
import org.zeromq.ZMQ.Context

import scala.concurrent.Future

class ZmqClient(context: Context, address: String) {
  val socket = context.socket(ZMQ.REQ)
  println(s"Connecting to server $address")
  socket.connect(address)
  val ec = EC.singleThreadedEc()

  def query[Resp <: PbMessage.Of[Resp]](
    request: GeneratedMessage,
    responseParser: GeneratedMessageCompanion[Resp]
  ): Future[Resp] = Future {
    println(s"Sending $request")
    socket.send(request.toByteArray, 0)
    responseParser.parseFrom(socket.recv(0))
  }(ec)

  def close(): Unit = {
    socket.close()
  }
}
