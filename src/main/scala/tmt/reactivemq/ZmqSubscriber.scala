package tmt.reactivemq

import akka.stream.scaladsl.Source
import com.trueaccord.scalapb.GeneratedMessageCompanion
import org.zeromq.ZMQ
import tmt.utils.{PbMessage, EC, ActorRuntime}

import scala.concurrent.Future

class ZmqSubscriber[Msg <: PbMessage.Of[Msg]](
  address: String,
  responseParser: GeneratedMessageCompanion[Msg],
  runtime: ActorRuntime
) {

  import runtime._

  private val socket = zmqContext.socket(ZMQ.SUB)
  println(s"Connecting to $address")
  socket.connect(address)
  socket.subscribe(Array.empty)

  private val ec = EC.singleThreadedEc()

  val stream = {
    Source
      .repeat(())
      .mapAsync(1)(_ => receive())
      .map(bytes => responseParser.parseFrom(bytes))
  }

  private def receive() = Future {
    socket.recv(0)
  }(ec)

  def shutdown(): Unit = {
    socket.close()
    ec.shutdown()
  }
}
