package tmt.app

case class Params(
  env: String = "dev",
  seedName: Option[String] = None,
  port: Int = 2552,
  roles: List[String] = List.empty
)
