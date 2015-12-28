package tmt.library

import com.typesafe.config._
import tmt.app.Params
import tmt.library.ConfigObjectExtensions.RichConfig
import collection.JavaConverters._

class ConfigLoader {

  def load(params: Params) = {
    val config = parse(params.env)

    val privateIp = IpInfo.privateIp(params.env)

    val bindingConfig = ConfigFactory.empty()
      .withPair("hostname", privateIp)
      .withPair("port", Integer.valueOf(params.port))
      .withPair("roles", params.roles.asJava)

    val binding = ConfigFactory.empty().withValue("binding", bindingConfig.root())

    config.withFallback(binding).resolve()
  }

  def parse(name: String) = ConfigFactory.load(
    name,
    ConfigParseOptions.defaults(),
    ConfigResolveOptions.defaults().setAllowUnresolved(true)
  )
}
