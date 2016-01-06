package tmt.demo.hcd

import akka.actor.{PoisonPill, Props}
import akka.cluster.singleton.{ClusterSingletonProxySettings, ClusterSingletonProxy, ClusterSingletonManagerSettings, ClusterSingletonManager}
import tmt.app.configs.{Names, AppSettings}
import tmt.app.utils.ActorRuntime
import tmt.demo.connectors.{ZmqToAkkaFlow, AkkaToZmqFlow}
import tmt.demo.zeromq_drivers.ZmqClient

class HcdSingleton(
  zmqClient: ZmqClient,
  akkaToZmqFlow: AkkaToZmqFlow,
  zmqToAkkaFlow: ZmqToAkkaFlow,
  appSettings: AppSettings,
  actorRuntime: ActorRuntime
) {

  import actorRuntime._

  lazy val start = system.actorOf(
    ClusterSingletonManager.props(
      singletonProps = Props(new HcdActor(
        zmqClient,
        akkaToZmqFlow,
        zmqToAkkaFlow,
        appSettings
      )),
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
