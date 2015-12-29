package tmt.mcs.clients

import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import akka.stream.scaladsl.Sink
import tmt.library.Connector
import tmt.utils.{ActorRuntime, PbMessage}

class SubscriberClient(actorRuntime: ActorRuntime) {

  import actorRuntime._

  def subscribe[Msg <: PbMessage.Of[Msg]](topic: String) = {
    val (sourceLinkedRef, source) = Connector.coupling[Msg](Sink.asPublisher(fanout = true))
    DistributedPubSub(system).mediator ! Subscribe(topic, sourceLinkedRef)
    source
  }
}
