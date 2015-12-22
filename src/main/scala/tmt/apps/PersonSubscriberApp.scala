package tmt.apps

import sample.Person
import tmt.reactivemq.{ActorConfigs, ZmqSubscriber}

object PersonSubscriberApp extends App {

  val default = ActorConfigs.create()
  import default._

  val subscriber = new ZmqSubscriber[Person](
    address = "tcp://localhost:5555",
    responseParser = Person,
    actorConfigs = default
  )


  subscriber.stream.take(100).runForeach { x =>
    println(s"Received $x")
  }.onComplete { x =>
    println(s"completed with value: $x")
    subscriber.stop()
    default.shutdown()
  }
}
