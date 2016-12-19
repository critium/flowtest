name := """flowtest"""
organization := "flowtest"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += filters
libraryDependencies += evolutions
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.5.10"
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0"
)
libraryDependencies += "com.h2database" % "h2" % "1.4.193"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "flowtest.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "flowtest.binders._"
