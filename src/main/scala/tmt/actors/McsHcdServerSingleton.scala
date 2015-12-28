package tmt.actors

import akka.actor.{PoisonPill, Props}
import akka.cluster.singleton.{ClusterSingletonManager, ClusterSingletonManagerSettings, ClusterSingletonProxy, ClusterSingletonProxySettings}
import tmt.app.Names
import tmt.reactivemq.ZmqClient
import tmt.utils.ActorRuntime

class McsHcdServerSingleton(zmqClient: ZmqClient, actorRuntime: ActorRuntime) {

  import actorRuntime._

  lazy val manager = system.actorOf(
    ClusterSingletonManager.props(
      singletonProps = Props(new McsHcdServer(zmqClient)),
      terminationMessage = PoisonPill,
      settings = ClusterSingletonManagerSettings(system).withRole(Names.HcdServer)
    ),
    name = Names.HcdServer
  )

  lazy val proxy = system.actorOf(
    ClusterSingletonProxy.props(
      singletonManagerPath = s"/user/${Names.HcdServer}",
      settings = ClusterSingletonProxySettings(system).withRole(Names.HcdServer)
    ),
    name = "hcdServerProxy"
  )
}
