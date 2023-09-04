ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "BridgeTrainer",
    idePackagePrefix := Some("space.tiagodinis33.bridgetrainer")
  )
resolvers ++= Seq(
  "Spigot" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies += "org.spigotmc" % "spigot-api" % "1.8.8-R0.1-SNAPSHOT"
