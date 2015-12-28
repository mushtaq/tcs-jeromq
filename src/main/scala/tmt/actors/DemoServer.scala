package tmt.actors

import tmt.app.Assembly

object DemoServer extends App {
  val assembly = args match {
    case Array(env, seedName) => new Assembly(env, Some(seedName))
    case Array(env) => new Assembly(env, None)
  }

  assembly.mcsHcdServerSingleton.manager
}
