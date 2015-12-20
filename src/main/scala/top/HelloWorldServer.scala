package top

import org.zeromq.ZMQ
import sample.Person
import sample.Person.{PhoneType, PhoneNumber}

import scala.util.{Try, Random}

object HelloWorldServer extends App {

  val context = ZMQ.context(1)
  val socket = context.socket(ZMQ.REP)
  socket.bind("tcp://*:5555")

  while (!Thread.currentThread.isInterrupted) {
    val reply = socket.recv(0)
    val person = Person.parseFrom(reply)
    println(s"Received : [$person]")
    socket.send(update(person).toByteArray, 0)
    Thread.sleep(1000)
  }

  def update(person: Person): Person = {
    val number = Random.nextInt(10000).toString
    val maybePhoneType = Try(PhoneType.values(Random.nextInt(4))).toOption
    val phoneNumber = PhoneNumber(number, maybePhoneType)
    person.addPhone(phoneNumber)
  }

  socket.close()
  context.term()
}
