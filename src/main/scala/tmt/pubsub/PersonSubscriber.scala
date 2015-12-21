package tmt.pubsub

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.zeromq.ZMQ
import tmt.reactivemq.ZmqSubscriber
import sample.Person

object PersonSubscriber extends App {

  val context = ZMQ.context(1)
  val subscriber = new ZmqSubscriber(context, "tcp://localhost:5555")

  implicit val system = ActorSystem()
  implicit val mat = ActorMaterializer()
  implicit val ec = system.dispatcher

  subscriber.stream(Person).take(100).runForeach { x =>
    println(s"Received $x")
  }.onComplete { x =>
    println(s"completed with value: $x")
    subscriber.stop()
    context.term()
    system.shutdown()
  }
}
