import com.trueaccord.scalapb.ScalaPbPlugin

name := "tcs-jeromq"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += Resolver.bintrayRepo("hseeberger", "maven")

libraryDependencies ++= Seq(
  "org.zeromq" % "jeromq" % "0.3.5",
  "com.typesafe.akka" %% "akka-stream" % "2.4.2-RC1",
  "com.typesafe.akka" %% "akka-cluster-tools" % "2.4.2-RC1",
  "org.scala-lang.modules" %% "scala-async" % "0.9.5",
  "com.softwaremill.macwire" %% "macros" % "2.2.2" % "provided",
  "com.softwaremill.macwire" %% "util" % "2.2.2",
  "com.github.alexarchambault" %% "case-app" % "0.3.0",
//  "de.heikoseeberger" %% "constructr-akka" % "0.8.2",

  "org.scalatest" %% "scalatest" % "2.2.5" % "test"
)

ScalaPbPlugin.protobufSettings

ScalaPbPlugin.runProtoc in ScalaPbPlugin.protobufConfig := (
  args => com.github.os72.protocjar.Protoc.runProtoc("-v261" +: args.toArray)
)
