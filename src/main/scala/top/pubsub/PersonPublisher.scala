package top.pubsub

import akka.stream.scaladsl.Source
import org.zeromq.ZMQ
import reactivemq.ZmqPublisher
import sample.Person
import scala.concurrent.ExecutionContext.Implicits.global

object PersonPublisher extends App {

  val context = ZMQ.context(1)
  val publisher = new ZmqPublisher(context, "tcp://*:5555")

  val numbers = Source(() => Iterator.from(1))
  val people = numbers.map(i => Person(name = s"mushtaq-$i", id = i))

  publisher
    .publish(people)
    .onComplete { x =>
      context.term()
    }

}
