import com.trueaccord.scalapb.ScalaPbPlugin

name := "tcs-jeromq"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.zeromq" % "jeromq" % "0.3.5",
  "com.typesafe.akka" %% "akka-stream-experimental" % "2.0.1",
  "com.typesafe.akka" %% "akka-cluster-tools" % "2.4.1",
  "org.scala-lang.modules" %% "scala-async" % "0.9.5",
  "com.softwaremill.macwire" %% "macros" % "2.2.0" % "provided",
  "com.softwaremill.macwire" %% "util" % "2.2.0",

  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

ScalaPbPlugin.protobufSettings

ScalaPbPlugin.runProtoc in ScalaPbPlugin.protobufConfig := (
  args => com.github.os72.protocjar.Protoc.runProtoc("-v261" +: args.toArray)
)
