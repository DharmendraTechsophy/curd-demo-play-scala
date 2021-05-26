name := """curd-demo-play"""
organization := "com.techsophy"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"


lazy val playVersion = "2.7.0"

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.play" %% "play-json" % playVersion,
  "com.typesafe.play" %% "play" % playVersion,
  "com.typesafe.play" %% "play-slick" % "4.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",
  "mysql" % "mysql-connector-java" % "6.0.6",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test,
)