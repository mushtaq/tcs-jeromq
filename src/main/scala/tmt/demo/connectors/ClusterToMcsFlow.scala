package tmt.demo.connectors

import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import akka.stream.scaladsl.Sink
import com.trueaccord.scalapb.GeneratedMessage
import tmt.app.library.Connector
import tmt.app.utils.ActorRuntime
import tmt.demo.zeromq_drivers.ZmqPublisherFactory

class ClusterToMcsFlow(actorRuntime: ActorRuntime, zmqPublisherFactory: ZmqPublisherFactory) {
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
