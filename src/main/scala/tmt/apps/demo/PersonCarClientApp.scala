package tmt.apps.demo

import sample.{Car, Person}
import tmt.app.Assembly

object PersonCarClientApp extends App {

  val assembly = new Assembly("dev", None)
  import assembly._

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
