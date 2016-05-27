name := "REST-API"

version := "1.0"

scalaVersion := "2.11.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  	"mysql" % "mysql-connector-java" % "5.1.34",
	"com.typesafe.play" %% "play-slick" % "2.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
    "com.typesafe.slick" %% "slick" % "3.1.1",
  	"com.github.tototoshi" %% "slick-joda-mapper" % "2.2.0",
  	"joda-time" % "joda-time" % "2.7",
  	"org.joda" % "joda-convert" % "1.7",
  	specs2 % Test,
  	"org.mockito" % "mockito-core" % "1.8.5"
)

resolvers ++= Seq(
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
)