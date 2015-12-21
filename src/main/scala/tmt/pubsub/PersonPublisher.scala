package tmt.pubsub

import akka.stream.scaladsl.Source
import sample.Person
import tmt.reactivemq.{ActorConfigs, ZmqPublisher}

object PersonPublisher extends App {

  val default = ActorConfigs.create()
  import default._

  val publisher = new ZmqPublisher[Person](
    address = "tcp://*:5555",
    actorConfigs = default
  )

  val numbers = Source(() => Iterator.from(1))
  val people = numbers.map(i => Person(name = s"mushtaq-$i", id = i))

  publisher
    .publish(people)
    .onComplete { x =>
      publisher.stop()
      default.shutdown()
    }

}
