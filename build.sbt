import com.trueaccord.scalapb.ScalaPbPlugin

name := "tcs-jeromq"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.zeromq" % "jeromq" % "0.3.5"
// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

ScalaPbPlugin.protobufSettings

ScalaPbPlugin.runProtoc in ScalaPbPlugin.protobufConfig := (
  args => com.github.os72.protocjar.Protoc.runProtoc("-v261" +: args.toArray)
)
