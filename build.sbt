name := "REST-API"

version := "1.0"

scalaVersion := "2.11.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
    "io.spray" % "spray-can" % "1.1-M8",
    "io.spray" % "spray-http" % "1.1-M8",
    "io.spray" % "spray-routing" % "1.1-M8",
    "com.typesafe.akka" %% "akka-actor" % "2.1.4",
    "com.typesafe.akka" %% "akka-slf4j" % "2.1.4",
  	"mysql" % "mysql-connector-java" % "5.1.34",
	"com.typesafe.play" %% "play-slick" % "1.1.0",	 
 	"com.typesafe.play" %% "play-slick-evolutions" % "1.1.0",
    "ch.qos.logback" % "logback-classic" % "1.0.13",
    "com.typesafe.slick" %% "slick" % "3.1.1",
  	"com.github.tototoshi" %% "slick-joda-mapper" % "2.2.0",
  	"joda-time" % "joda-time" % "2.7",
  	"org.joda" % "joda-convert" % "1.7"
)

resolvers ++= Seq(
    "Spray repository" at "http://repo.spray.io",
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)