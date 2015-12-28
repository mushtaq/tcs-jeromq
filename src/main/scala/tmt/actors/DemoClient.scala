package tmt.actors

import tcsstr2.Transition
import tmt.app.Assembly

object DemoClient extends App {

  val assembly = args match {
    case Array(env, seedName) => new Assembly(env, Some(seedName))
    case Array(env) => new Assembly(env, None)
  }

  import assembly._

  Thread.sleep(1000)
  mcsHcdClient.lifecycle(Transition.STARTUP)
  Thread.sleep(1000)
  mcsHcdClient.lifecycle(Transition.STARTUP)
  Thread.sleep(1000)
  mcsHcdClient.lifecycle(Transition.STARTUP)
  Thread.sleep(1000)
  mcsHcdClient.lifecycle(Transition.STARTUP)

}
