package tmt.library

import com.typesafe.config._
import tmt.library.ConfigObjectExtensions.RichConfig

class ConfigLoader {

  def load(env: String, seedName: Option[String]) = {
    val config = parse(env)

    val (privateIp, port) = seedName match {
      case Some(seed) => (config.getString(s"$seed.hostname"), config.getInt(s"$seed.port"))
      case None       => IpInfo.privateIpAndPort(env)
    }

    val bindingConfig = ConfigFactory.empty()
      .withPair("hostname", privateIp)
      .withPair("port", Integer.valueOf(port))

    val binding = ConfigFactory.empty().withValue("binding", bindingConfig.root())

    config.withFallback(binding).resolve()
  }

  def parse(name: String) = ConfigFactory.load(
    name,
    ConfigParseOptions.defaults(),
    ConfigResolveOptions.defaults().setAllowUnresolved(true)
  )
}
