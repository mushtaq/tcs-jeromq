package tmt.app.configs

case class Params(
  env: String = "dev",
  port: Int = 2552,
  roles: List[String] = List.empty,
  system: String = "tcs"
) {
  def addRole(role: String) = copy(roles = role :: roles)
  def configName = s"$system-$env"
}
