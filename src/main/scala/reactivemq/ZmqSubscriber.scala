package reactivemq

import akka.stream.scaladsl.Source
import com.trueaccord.scalapb.GeneratedMessageCompanion
import org.zeromq.ZMQ
import org.zeromq.ZMQ.Context

import scala.concurrent.Future

class ZmqSubscriber(context: Context, address: String) {
  val socket = context.socket(ZMQ.SUB)
  println(s"Connecting to $address")
  socket.connect(address)
  socket.subscribe(Array.empty)

  val ec = EC.singleThreadedEc()

  def stream[Resp <: PbMessage.Of[Resp]](responseParser: GeneratedMessageCompanion[Resp]) = {
    Source
      .repeat(())
      .mapAsync(1)(_ => receive())
      .map(bytes => responseParser.parseFrom(bytes))
  }

  def receive() = Future {
    socket.recv(0)
  }(ec)

  def stop(): Unit = {
    socket.close()
  }

}
