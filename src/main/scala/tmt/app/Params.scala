package tmt.app

case class Params(
  env: String = "dev",
  port: Int = 2552,
  roles: List[String] = List.empty
)
