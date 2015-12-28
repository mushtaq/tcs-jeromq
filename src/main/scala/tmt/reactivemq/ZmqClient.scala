package tmt.reactivemq

import com.trueaccord.scalapb.{GeneratedMessage, GeneratedMessageCompanion}
import org.zeromq.ZMQ
import tmt.app.AppSettings
import tmt.utils.{PbMessage, EC, ActorRuntime}

import scala.concurrent.Future

class ZmqClient(settings: AppSettings, runtime: ActorRuntime) {

  import runtime._

  val address = s"tcp://${settings.mcsIp}:${settings.mcsCommandsPort}"

  private val socket = zmqContext.socket(ZMQ.REQ)
  println(s"Connecting to server $address")
  socket.connect(address)
  private val ec = EC.singleThreadedEc()

  def query[Req <: GeneratedMessage, Res <: PbMessage.Of[Res]](
    request: Req,
    responseParser: GeneratedMessageCompanion[Res]
  ): Future[Res] = Future {
    println(s"Sending $request")
    socket.send(request.toByteArray, 0)
    responseParser.parseFrom(socket.recv(0))
  }(ec)

  def shutdown(): Unit = {
    socket.close()
    ec.shutdown()
  }
}
