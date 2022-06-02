ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "PTRLab4"
  )

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-json" % "2.9.2",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.6.19",
    "ch.qos.logback" % "logback-classic" % "1.2.11",
    "com.softwaremill.sttp.client3" %% "core" % "3.6.2"
)
