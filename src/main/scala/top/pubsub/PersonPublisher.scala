package top.pubsub

import org.zeromq.ZMQ
import sample.Person

object PersonPublisher extends App {

  val context = ZMQ.context(1)
  val socket = context.socket(ZMQ.PUB)
  socket.bind("tcp://*:5555")

  var requestNbr = 0

  while (!Thread.currentThread.isInterrupted) {
    val person = Person(name = "mushtaq", id = requestNbr)
    println(s"Publishing: [$person]")
    socket.send(person.toByteArray, 0)
    requestNbr += 1
    Thread.sleep(100)
  }

  socket.close()
  context.term()
}
