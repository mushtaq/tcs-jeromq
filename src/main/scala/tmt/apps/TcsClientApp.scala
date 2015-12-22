package tmt.apps

import sample.{Car, Person}
import tmt.reactivemq.{TcsClient, ZmqClient}
import tmt.utils.ActorRuntime

object TcsClientApp extends App {

  val runtime = ActorRuntime.create()

  import runtime._

  val zmqClient = new ZmqClient(
    address = "tcp://localhost:5555",
    runtime = runtime
  )

  val tcsClient = new TcsClient(zmqClient)

  (1 to 10).foreach { requestNbr =>
    val person = Person(name = s"mushtaq-$requestNbr", id = requestNbr)
    tcsClient.update(person).foreach(p => println(s"received: $p"))
  }

  (1 to 10).foreach { requestNbr =>
    val car = Car("Nissan", "Micra")
    tcsClient.service(car).foreach(p => println(s"received: $p"))
  }

  Thread.sleep(10000)
  zmqClient.shutdown()
  runtime.shutdown()

}
