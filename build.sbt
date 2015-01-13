name := """play-thymeleaf-plugin"""

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.thymeleaf" % "thymeleaf" % "2.1.3.RELEASE",
  "javax.servlet" % "javax.servlet-api" % "3.1.0"
)

lazy val root = (project in file(".")).aggregate(plugin, sample)

lazy val plugin = (project in file("plugin")).settings(
	libraryDependencies ++= Seq("org.thymeleaf" % "thymeleaf" % "2.1.3.RELEASE",
	"javax.servlet" % "javax.servlet-api" % "3.1.0")
	).enablePlugins(PlayScala)

lazy val sample = (project in file("sample")).enablePlugins(PlayScala).dependsOn(plugin)
