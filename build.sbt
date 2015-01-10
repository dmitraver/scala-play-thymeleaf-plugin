name := """play-thymeleaf-plugin"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.4"


lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
	"org.thymeleaf" % "thymeleaf" % "2.1.3.RELEASE",
	"javax.servlet" % "javax.servlet-api" % "3.1.0"
)
