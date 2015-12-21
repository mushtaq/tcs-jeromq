package tmt.reactivemq

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import org.zeromq.ZMQ

import scala.concurrent.ExecutionContext

class ActorConfigs(_system: ActorSystem, _mat: Materializer, _ec: ExecutionContext) {
  implicit val system = _system
  implicit val mat    = _mat
  implicit val ec     = _ec
  val zmqContext = ZMQ.context(1)

  def shutdown() = {
    system.shutdown()
    zmqContext.close()
  }
}

object ActorConfigs {
  def create(): ActorConfigs = {
    lazy val system = ActorSystem()
    lazy val mat    = ActorMaterializer()(system)
    lazy val ec     = system.dispatcher
    new ActorConfigs(system, mat, ec)
  }
}
