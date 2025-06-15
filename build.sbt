name := "usdf"

version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies += "org.scalactic"              %% "scalactic"                 % "3.2.9"
libraryDependencies += "org.scalatest"              %% "scalatest"                 % "3.2.9" % "test"
libraryDependencies += "com.github.alexarchambault" %% "scalacheck-shapeless_1.15" % "1.3.0"
libraryDependencies += "org.clapper"                %% "grizzled-slf4j"            % "1.3.4"
libraryDependencies += "ch.qos.logback"             % "logback-classic"            % "1.1.8"
libraryDependencies += "ch.qos.logback"             % "logback-core"               % "1.1.8"
libraryDependencies += "ch.qos.logback"             % "logback-access"             % "1.1.8"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % "0.14.1",
  "io.circe" %% "circe-generic" % "0.14.1",
  "io.circe" %% "circe-parser" % "0.14.1"
)
