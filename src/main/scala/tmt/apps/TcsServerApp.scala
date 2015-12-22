package tmt.apps

import sample.Command.Msg.{Empty, ServiceCar, UpdatePerson}
import sample.PhoneNumber.PhoneType
import sample._
import tmt.reactivemq.{ActorConfigs, ZmqServer}

import scala.util.{Random, Try}

object TcsServerApp extends App {

//  println(B.parseFrom(A("hello", 100).toByteArray)) // B(0, "")
//  println(A.parseFrom(B(100, "hello").toByteArray)) // A("", 0)

  val default = ActorConfigs.create()

  import default._

  val server = new ZmqServer(
    address = "tcp://*:5555",
    actorConfigs = default
  )

  server.start {
    case UpdatePerson(person) =>
      val number = Random.nextInt(10000).toString
      val maybePhoneType = Try(PhoneType.values(Random.nextInt(4))).toOption
      val phoneNumber = PhoneNumber(number, maybePhoneType)
      person.addPhone(phoneNumber)
    case ServiceCar(car)      =>
      car.addServiceTimestamps(System.currentTimeMillis())
    case Empty                =>
      ErrorMsg("something has gone wrong")
  }.onComplete { x =>
    server.stop()
    default.shutdown()
  }

}
