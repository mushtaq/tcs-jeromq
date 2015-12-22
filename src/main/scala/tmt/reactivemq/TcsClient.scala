package tmt.reactivemq

import sample.Command.Msg.{ServiceCar, UpdatePerson}
import sample.{Command, Car, Person}

import scala.concurrent.Future

class TcsClient(zmqClient: ZmqClient) {

  def update(person: Person): Future[Person] = {
    val command = Command().withMsg(UpdatePerson(person))
    zmqClient.query(command, Person)
  }

  def service(car: Car): Future[Car] = {
    val command = Command().withMsg(ServiceCar(car))
    zmqClient.query(command, Car)
  }
}
