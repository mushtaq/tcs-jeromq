package tmt.demo.hcd

import caseapp._
import tmt.app.configs.{Assembly, Names, Params}

object HcdService extends AppOf[HcdServiceInner] {
  def parser = default
}

case class HcdServiceInner(params: Params) extends App {
  val updatedParams = params.addRole(Names.HcdServer)

  val hcdSingleton = new Assembly(updatedParams).hcdSingleton
  //start hcd
  hcdSingleton.start
}
