package tmt.demo.hcd_drivers

import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import akka.stream.scaladsl.Sink
import tmt.app.library.Connector
import tmt.app.utils.{ActorRuntime, PbMessage}

class EventSubscriber(actorRuntime: ActorRuntime) {

  import actorRuntime._

  def subscribe[Msg <: PbMessage.Of[Msg]](topic: String) = {
    val (sourceLinkedRef, source) = Connector.coupling[Msg](Sink.asPublisher(fanout = true))
    DistributedPubSub(system).mediator ! Subscribe(topic, sourceLinkedRef)
    source
  }
}
