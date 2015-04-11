
import SonatypeKeys._

lazy val appPublishMavenStyle = true

lazy val appPublishArtifactInTest = false

lazy val appPomIncludeRepository = { _: MavenRepository => false }

lazy val appPomExtra = {
				<url>https://github.com/dmitraver/scala-play-thymeleaf-plugin</url>
								<licenses>
									<license>
										<name>Apache License, Version 2.0</name>
										<url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
										<distribution>repo</distribution>
									</license>
								</licenses>
								<scm>
									<url>git@github.com:dmitraver/scala-play-thymeleaf-plugin.git</url>
									<connection>scm:git:git@github.com:dmitraver/scala-play-thymeleaf-plugin.git</connection>
								</scm>
								<developers>
									<developer>
										<id>dmitraver</id>
										<name>Dmitry Avershin</name>
										<url>https://github.com/dmitraver</url>
									</developer>
								</developers>
}

lazy val root = (project in file(".")).aggregate(plugin, sample)

lazy val plugin = (project in file("plugin")).settings(
	name := "play-thymeleaf-plugin",
  version := "1.0",
	organization := "com.github.dmitraver",
	pomExtra := appPomExtra,
	publishMavenStyle := appPublishMavenStyle,
  publishArtifact in Test := appPublishArtifactInTest,
	pomIncludeRepository := appPomIncludeRepository,
	publishTo := {
		val nexus = "https://oss.sonatype.org/"
		if (isSnapshot.value)
			Some("snapshots" at nexus + "content/repositories/snapshots")
		else
			Some("releases"  at nexus + "service/local/staging/deploy/maven2")
	},
	libraryDependencies ++= Seq(
		"org.thymeleaf" % "thymeleaf" % "2.1.3.RELEASE",
		"javax.servlet" % "javax.servlet-api" % "3.1.0",
		"com.typesafe.play" %% "play" % play.core.PlayVersion.current % "provided")
).settings(sonatypeSettings: _*)

lazy val sample = (project in file("sample")).settings(
	libraryDependencies ++= Seq(
		jdbc,
		anorm,
		cache,
		ws,
		"org.thymeleaf" % "thymeleaf" % "2.1.3.RELEASE",
		"javax.servlet" % "javax.servlet-api" % "3.1.0",
  	"com.github.dmitraver" % "play-thymeleaf-plugin_2.10" % "1.0"
)).enablePlugins(PlayScala)
