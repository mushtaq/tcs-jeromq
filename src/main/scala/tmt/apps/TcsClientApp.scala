package tmt.apps

import sample.{Car, Person}
import tmt.reactivemq.{ActorConfigs, TcsClient, ZmqClient}

object TcsClientApp extends App {

  val default = ActorConfigs.create()

  import default._

  val zmqClient = new ZmqClient(
    address = "tcp://localhost:5555",
    actorConfigs = default
  )

  private val tcsClient = new TcsClient(zmqClient)

  (1 to 10).foreach { requestNbr =>
    val person = Person(name = s"mushtaq-$requestNbr", id = requestNbr)
    tcsClient.update(person).foreach(p => println(s"received: $p"))
  }

  (1 to 10).foreach { requestNbr =>
    val car = Car("Nissan", "Micra")
    tcsClient.service(car).foreach(p => println(s"received: $p"))
  }

  Thread.sleep(10000)
  zmqClient.close()
  default.shutdown()

}
