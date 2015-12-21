package reactivemq

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import com.trueaccord.scalapb.GeneratedMessage
import org.zeromq.ZMQ
import org.zeromq.ZMQ.Context

import scala.concurrent.Future

class ZmqPublisher(context: Context, address: String) {

  val socket = context.socket(ZMQ.PUB)
  socket.bind(address)

  val ec = EC.singleThreadedEc()
  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()

  def publish(messages: Source[GeneratedMessage, Any]) = {
    messages
      .mapAsync(1)(publishSingle)
      .runWith(Sink.ignore)
      .map { x =>
        println(s"completed with value: $x")
        system.shutdown()
        socket.close()
      }(system.dispatcher)
  }

  def publishSingle(message: GeneratedMessage) = Future {
    println(s"Publishing: [$message]")
    Thread.sleep(100)
    socket.send(message.toByteArray, 0)
  }(ec)

  def stop(): Unit = {
    socket.close()
  }

}
