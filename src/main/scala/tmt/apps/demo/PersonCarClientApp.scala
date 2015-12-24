package tmt.apps.demo

import sample.{Car, Person}
import tmt.reactivemq.{SampleClient, ZmqClient}
import tmt.utils.ActorRuntime

object PersonCarClientApp extends App {

  val runtime = ActorRuntime.create()

  import runtime._

  val zmqClient = new ZmqClient(
    address = "tcp://localhost:5555",
    runtime = runtime
  )

  val sampleClient = new SampleClient(zmqClient)

  (1 to 10).foreach { requestNbr =>
    val person = Person(name = s"mushtaq-$requestNbr", id = requestNbr)
    sampleClient.update(person).foreach(p => println(s"received: $p"))
  }

  (1 to 10).foreach { requestNbr =>
    val car = Car("Nissan", "Micra")
    sampleClient.service(car).foreach(p => println(s"received: $p"))
  }

  Thread.sleep(100000)
  zmqClient.shutdown()
  runtime.shutdown()

}
