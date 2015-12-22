package tmt.apps

import sample.Person
import tmt.reactivemq.{ActorRuntime, ZmqSubscriber}

object PersonSubscriberApp extends App {

  val runtime = ActorRuntime.create()
  import runtime._

  val subscriber = new ZmqSubscriber[Person](
    address = "tcp://localhost:5555",
    responseParser = Person,
    runtime = runtime
  )

  subscriber.stream.runForeach { x =>
    println(s"Received $x")
  }.onComplete { x =>
    println(s"completed with value: $x")
    subscriber.shutdown()
    runtime.shutdown()
  }
}
