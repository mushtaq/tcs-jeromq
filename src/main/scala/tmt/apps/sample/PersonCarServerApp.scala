package tmt.apps.sample

import sample.Command.Msg.{Empty, ServiceCar, UpdatePerson}
import sample.PhoneNumber.PhoneType
import sample._
import tmt.app.{Params, Assembly}

import scala.util.{Random, Try}
import caseapp._

case class PersonCarServer(params: Params) extends App {

//  println(B.parseFrom(A("hello", 100).toByteArray)) // B(0, "")
//  println(A.parseFrom(B(100, "hello").toByteArray)) // A("", 0)

  val assembly = new Assembly(params)
  import assembly._

  zmqServer.start(Command) { command =>
    command.msg match {
      case UpdatePerson(person) =>
        val number = Random.nextInt(10000).toString
        val maybePhoneType = Try(PhoneType.values(Random.nextInt(4))).toOption
        val phoneNumber = PhoneNumber(number, maybePhoneType)
        person.addPhone(phoneNumber)
      case ServiceCar(car)      =>
        car.addServiceTimestamps(System.currentTimeMillis())
      case Empty                =>
        ErrorMsg("something has gone wrong")
    }
  }.onComplete { x =>
    zmqServer.shutdown()
    runtime.shutdown()
  }

}

object PersonCarServerApp extends AppOf[PersonCarServer] {
  def parser = default
}
