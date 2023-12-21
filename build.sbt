
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.2.1"

lazy val bridge_trainer = (project in file("."))
  .settings(
    assembly / assemblyShadeRules := Seq(
      ShadeRule.rename(
        "org.yaml.snakeyaml.**" -> "space.tiagodinis33.bridgetrainer.yaml.@0",
        "scala.**" -> "space.tiagodinis33.bridgetrainer.scala.@0"
      )
        .inAll
    ),
    name := "BridgeTrainer",
    idePackagePrefix := Some("space.tiagodinis33.bridgetrainer")
  )
resolvers ++= Seq(
  "Spigot" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies += "org.spigotmc" % "spigot-api" % "1.8.8-R0.1-SNAPSHOT" % "provided"
libraryDependencies += "org.yaml" % "snakeyaml" % "2.0"
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")