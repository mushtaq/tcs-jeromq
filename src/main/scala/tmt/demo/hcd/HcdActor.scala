package tmt.demo.hcd

import akka.actor.Actor
import akka.pattern.pipe
import tcsstr2.{Tcs_Command, command_response, mcs_DriveStatus, tcs_mcs_PositionDemand}
import tmt.app.configs.{AppSettings, Names}
import tmt.demo.connectors.{McsToClusterFlow, ClusterToMcsFlow}
import tmt.demo.zeromq_drivers.ZmqClient

class HcdActor(
  zmqClient: ZmqClient,
  clusterToMcsFlow: ClusterToMcsFlow,
  mcsToClusterFlow: McsToClusterFlow,
  appSettings: AppSettings
) extends Actor {

  import context.dispatcher

  override def preStart() = {

    //start event subscriber hcd
    clusterToMcsFlow.connect[tcs_mcs_PositionDemand](
      subscriberTopic = Names.PositionDemands,
      publishingPort = appSettings.mcsPositionDemandPort
    )

    //start event publishing hcd
    mcsToClusterFlow.connect(
      publishingTopic = Names.DriveStatus,
      subscriberPort = appSettings.mcsDriveStatusPort,
      responseParser = mcs_DriveStatus
    )
  }

  //command server hcd
  def receive = {
    case command: Tcs_Command =>
      println(s"***** mcs hcd server received $command from ${sender()}")
      val result = zmqClient.query(command, command_response)
      result pipeTo sender()
  }
}

