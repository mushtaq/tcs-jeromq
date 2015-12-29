package tmt.mcs.reactivemq

import akka.stream.scaladsl.{Sink, Source}
import com.trueaccord.scalapb.GeneratedMessage
import org.zeromq.ZMQ
import tmt.utils.{EC, ActorRuntime}

import scala.concurrent.Future

class ZmqPublisher[Msg <: GeneratedMessage](port: Int, runtime: ActorRuntime) {

  import runtime._

  val address = s"tcp://*:$port"

  private val socket = zmqContext.socket(ZMQ.PUB)
  socket.bind(address)

  private val ec = EC.singleThreadedEc()

  def publish(messages: Source[Msg, Any]) = {
    messages
      .mapAsync(1)(publishSingle)
      .runWith(Sink.ignore)
      .map { x =>
        println(s"completed with value: $x")
      }(system.dispatcher)
  }

  def publishSingle(message: Msg) = Future {
    println(s"********** ZmqPublisher is publishing: [$message]")
    Thread.sleep(100)
    socket.send(message.toByteArray, 0)
  }(ec)

  def shutdown(): Unit = {
    socket.close()
    ec.shutdown()
  }
}

class ZmqPublisherFactory(runtime: ActorRuntime) {
  def make[Msg <: GeneratedMessage](port: Int) = new ZmqPublisher[Msg](port, runtime)
}
