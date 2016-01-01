package tmt.demo.hcds

import akka.actor.{PoisonPill, Props, Actor}
import akka.cluster.singleton.{ClusterSingletonProxySettings, ClusterSingletonProxy, ClusterSingletonManagerSettings, ClusterSingletonManager}
import akka.pattern.pipe
import tcsstr2.{Tcs_Command, command_response}
import tmt.app.configs.Names
import tmt.app.utils.ActorRuntime
import tmt.demo.zeromq_drivers.ZmqClient

class CommandHcd(zmqClient: ZmqClient) extends Actor {

  import context.dispatcher

  def receive = {
    case command: Tcs_Command =>
      println(s"***** mcs hcd server received $command from ${sender()}")
      val result = zmqClient.query(command, command_response)
      result pipeTo sender()
  }
}


class CommandHcdSingleton(zmqClient: ZmqClient, actorRuntime: ActorRuntime) {

  import actorRuntime._

  lazy val manager = system.actorOf(
    ClusterSingletonManager.props(
      singletonProps = Props(new CommandHcd(zmqClient)),
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
