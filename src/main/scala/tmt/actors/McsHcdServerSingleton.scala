package tmt.actors

import akka.actor.{PoisonPill, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Put
import akka.cluster.singleton.{ClusterSingletonManagerSettings, ClusterSingletonManager}
import tmt.app.Names
import tmt.reactivemq.ZmqClient
import tmt.utils.ActorRuntime

class McsHcdServerSingleton(zmqClient: ZmqClient, actorRuntime: ActorRuntime) {

  import actorRuntime._

  lazy val hcdServerRef = system.actorOf(
    ClusterSingletonManager.props(
      singletonProps = Props(new McsHcdServer(zmqClient)),
      terminationMessage = PoisonPill,
      settings = ClusterSingletonManagerSettings(system)
    ),
    name = Names.HcdServer
  )

  DistributedPubSub(system).mediator ! Put(hcdServerRef)

}
