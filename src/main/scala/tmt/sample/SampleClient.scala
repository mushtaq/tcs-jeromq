package tmt.sample

import sample.Command.Msg.{ServiceCar, UpdatePerson}
import sample.{Car, Command, Person}
import tmt.demo.zeromq_drivers.ZmqClient

import scala.concurrent.Future

class SampleClient(zmqClient: ZmqClient) {

  def update(person: Person): Future[Person] = {
    val command = Command().withMsg(UpdatePerson(person))
    zmqClient.query(command, Person)
  }

  def service(car: Car): Future[Car] = {
    val command = Command().withMsg(ServiceCar(car))
    zmqClient.query(command, Car)
  }
}
