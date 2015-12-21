package tmt.reactivemq

import com.trueaccord.scalapb.{GeneratedMessage, GeneratedMessageCompanion}
import org.zeromq.ZMQ

import scala.concurrent.Future

class ZmqClient[Req <: GeneratedMessage, Res <: PbMessage.Of[Res]](
  address: String,
  responseParser: GeneratedMessageCompanion[Res],
  actorConfigs: ActorConfigs
) {

  import actorConfigs._

  private val socket = zmqContext.socket(ZMQ.REQ)
  println(s"Connecting to server $address")
  socket.connect(address)
  private val ec = EC.singleThreadedEc()

  def query(request: Req): Future[Res] = Future {
    println(s"Sending $request")
    socket.send(request.toByteArray, 0)
    responseParser.parseFrom(socket.recv(0))
  }(ec)

  def close(): Unit = {
    socket.close()
    ec.shutdown()
  }
}
