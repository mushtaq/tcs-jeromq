package tmt.library

import java.net.InetAddress

import com.typesafe.config._
import tmt.app.Params
import tmt.library.ConfigObjectExtensions.RichConfig

class ConfigLoader {

  def load(params: Params) = {
    val config = parse(params.env)

    val privateIp = IpInfo.privateIp(params.env)

    val bindingConfig = ConfigFactory.empty()
      .withPair("hostname", privateIp)
      .withPair("port", Integer.valueOf(params.port))

    val binding = ConfigFactory.empty().withValue("binding", bindingConfig.root())

    config.withFallback(binding).resolve()
  }

  def parse(name: String) = ConfigFactory.load(
    name,
    ConfigParseOptions.defaults(),
    ConfigResolveOptions.defaults().setAllowUnresolved(true)
  )
}
