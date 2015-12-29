package tmt.mcs.hcd

import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import akka.stream.scaladsl.Sink
import com.trueaccord.scalapb.GeneratedMessage
import tmt.library.Connector
import tmt.mcs.reactivemq.ZmqPublisherFactory
import tmt.utils.ActorRuntime

class PublisherHcd(actorRuntime: ActorRuntime, zmqPublisherFactory: ZmqPublisherFactory) {
  import actorRuntime._

  def connect[Msg <: GeneratedMessage](subscriberTopic: String, publishingPort: Int) = {
    val (sourceLinkedRef, source) = Connector.coupling[Msg](Sink.asPublisher(fanout = false))
    DistributedPubSub(system).mediator ! Subscribe(subscriberTopic, sourceLinkedRef)
    val zmqPublisher = zmqPublisherFactory.make[Msg](publishingPort)

    zmqPublisher
      .publish(source)
      .onComplete { x =>
        zmqPublisher.shutdown()
      }
  }
}
