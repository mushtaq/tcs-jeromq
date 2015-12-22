package tmt.utils

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import org.zeromq.ZMQ

import scala.concurrent.ExecutionContext

class ActorRuntime(_system: ActorSystem, _mat: Materializer, _ec: ExecutionContext) {
  implicit val system = _system
  implicit val mat    = _mat
  implicit val ec     = _ec
  val zmqContext = ZMQ.context(1)

  def shutdown() = {
    system.shutdown()
    zmqContext.close()
  }
}

object ActorRuntime {
  def create(): ActorRuntime = {
    lazy val system = ActorSystem()
    lazy val mat    = ActorMaterializer()(system)
    lazy val ec     = system.dispatcher
    new ActorRuntime(system, mat, ec)
  }
}
