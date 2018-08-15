import CommonBuild._
import Versions._
import com.typesafe.sbt.packager.docker.{Cmd, ExecCmd}
import uk.gov.hmrc.gitstamp.GitStampPlugin._

organization in ThisBuild := "it.gov.***REMOVED***"
name := "***REMOVED***-security-manager"

val isStaging = System.getProperty("STAGING") != null

Seq(gitStampSettings: _*)

version in ThisBuild := sys.***REMOVED***.get("SECURITY_MANAGER_VERSION").getOrElse("1.0.3-SNAPSHOT")

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8", // yes, this is 2 args
  "-feature",
  "-unchecked",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-dead-code",
  "-Xfuture"
)

//addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")


unmanagedBase :=baseDirectory.value / "lib/ClouderaImpalaJDBC41_2.5.43" //TODO to put on Nexus



//wartremoverErrors ++= Warts.allBut(Wart.Nothing, Wart.PublicInference, Wart.Any, Wart.Equals, Wart.Option2Iterable)
//wartremoverExcluded ++= getRecursiveListOfFiles(baseDirectory.value / "target" / "scala-2.11" / "routes").toSeq
//wartremoverExcluded ++= routes.in(Compile).value

/*
lazy val client = (project in file("client")).
  settings(Seq(
    name := "***REMOVED***-security-manager-client",
    swaggerGenerateClient := true,
    swaggerClientCodeGenClass := new it.gov.***REMOVED***.swaggergenerators.DafClientGenerator,
    swaggerCodeGenPackage := "it.gov.***REMOVED***.securitymanager",
    swaggerModelFilesSplitting := "oneFilePerModel",
    swaggerSourcesDir := file(s"${baseDirectory.value}/../conf"),
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % playVersion,
      "com.typesafe.play" %% "play-ws" %  playVersion
    )
  )).
  enablePlugins(SwaggerCodegenPlugin)*/

lazy val root = (project in file(".")).
  enablePlugins(PlayScala, ApiFirstCore, ApiFirstPlayScalaCodeGenerator, ApiFirstSwaggerParser, /*AutomateHeaderPlugin,*/ DockerPlugin)//.
  //dependsOn(client).aggregate(client)


scalaVersion in ThisBuild := "2.11.11"

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.webjars" % "swagger-ui" % swaggerUiVersion,
  "it.gov.***REMOVED***" %% "common" % Versions.***REMOVED***CommonVersion,
  "org.mongodb" %% "casbah" % "3.1.1",
  "ch.lightshed" %% "courier" % "0.1.4",
  "org.typelevel" %% "cats-core" % "1.0.0-RC1",
  //"com.github.cb372" %% "scalacache-guava" % "0.9.4",
  "com.jcraft" % "jsch" % "0.1.54",
  specs2 % Test,
  "org.scalactic" %% "scalactic" % "3.0.4" % "test",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)

libraryDependencies ++= Seq(
  "io.prometheus" % "simpleclient" % "0.1.0",
  "io.prometheus" % "simpleclient_hotspot" % "0.1.0",
  "io.prometheus" % "simpleclient_common" % "0.1.0"
)

libraryDependencies ++= Seq(
  //"commons-codec" % "commons-codec" % "1.3",
  //"commons-logging" % "commons-logging" % "1.1.1",
  "org.apache.***REMOVED***components" % "***REMOVED***client" % "4.1.3",
  "org.apache.***REMOVED***components" % "***REMOVED***core" % "4.1.3",
  "org.apache.thrift" % "libfb303" % "0.9.0",
  "org.apache.thrift" % "libthrift" % "0.9.0"
  //"log4j" % "log4j" % "1.2.14",
  //"org.slf4j" % "slf4j-api" % "1.5.11",
  //"org.slf4j" % "slf4j-log4j12" % "1.5.11",
)

val zookeeperExcludes =
  (moduleId: ModuleID) => moduleId.
    exclude("org.slf4j", "slf4j-log4j12")

val zookeeperLibs = Seq(
  zookeeperExcludes("org.apache.zookeeper" % "zookeeper" % "3.4.6")
)
libraryDependencies ++= zookeeperLibs

//Resolver.url("sbt-plugins", url("***REMOVED***://dl.bintray.com/zalando/sbt-plugins"))(Resolver.ivyStylePatterns),

resolvers ++= Seq(
  "zalando-bintray" at "***REMOVED***://dl.bintray.com/zalando/maven",
  "scalaz-bintray" at "***REMOVED***://dl.bintray.com/scalaz/releases",
  "jeffmay" at "***REMOVED***://dl.bintray.com/jeffmay/maven",
  Resolver.url("sbt-plugins", url("***REMOVED***://dl.bintray.com/gruggiero/sbt-plugins"))(Resolver.ivyStylePatterns),
  "cloudera" at "***REMOVED***://repository.cloudera.com/artifactory/cloudera-repos/",
  "lightshed-maven" at "***REMOVED***://dl.bintray.com/content/lightshed/maven",
  Resolver.mavenLocal
)

resolvers ++= { if(isStaging) Seq("***REMOVED*** repo" at "***REMOVED***://nexus.***REMOVED***.test:8081/repository/maven-public/")
                else Seq("***REMOVED*** repo" at "***REMOVED***://nexus.***REMOVED***.***REMOVED***.it:8081/repository/maven-public/")}

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

apiFirstParsers := Seq(ApiFirstSwaggerParser.swaggerSpec2Ast.value).flatten

playScalaAutogenerateTests := false

playScalaCustomTemplateLocation := Some(baseDirectory.value / "templates")

licenses += ("Apache-2.0", new URL("***REMOVED***://www.apache.org/licenses/LICENSE-2.0.txt"))
headerLicense := Some(HeaderLicense.ALv2("2017", "TEAM PER LA TRASFORMAZIONE DIGITALE"))
headerMappings := headerMappings.value + (HeaderFileType.conf -> HeaderCommentStyle.HashLineComment)

dockerPackageMappings in Docker += (if(isStaging) baseDirectory.value / "cert" / "test" / "jssecacerts"
                                    else baseDirectory.value / "cert" / "jssecacerts") -> "jssecacerts"

dockerPackageMappings in Docker += (baseDirectory.value / "script/kb_init.sh") -> "opt/docker/script/kb_init.sh"


//dockerBaseImage := "anapsix/alpine-java:8_jdk_unlimited"
dockerBaseImage := "openjdk:8u171-jdk-slim"
dockerCommands := dockerCommands.value.flatMap {
  case cmd@Cmd("FROM", _) => List(cmd,
    //Cmd("RUN", "apk update && apk add bash krb5-libs krb5 curl"),
    Cmd("RUN", "apt-get update"),
    Cmd("RUN", "DEBIAN_FRONTEND=noninteractive apt-get install -y bash krb5-user curl"),
//    Cmd("RUN", "ln -sf /etc/krb5.conf /opt/jdk/jre/lib/security/krb5.conf"),
    Cmd("RUN", "ln -sf /etc/krb5.conf /docker-java-home/jre/lib/security/krb5.conf"),
//    Cmd("COPY", "jssecacerts", "/opt/jdk/jre/lib/security/")
    Cmd("COPY", "jssecacerts", "/docker-java-home/jre/lib/security")

  )
  case other => List(other)
}

dockerExposedPorts := Seq(9000)

dockerEntrypoint := {Seq(s"bin/${name.value}", "-Dconfig.file=conf/production.conf")}

dockerRepository := { if(isStaging)Option("nexus.***REMOVED***.test") else Option("nexus.***REMOVED***.***REMOVED***.it") }


publishTo in ThisBuild := {
  val nexus = if(isStaging) "***REMOVED***://nexus.***REMOVED***.test:8081/repository/"
              else "***REMOVED***://nexus.***REMOVED***.***REMOVED***.it:8081/repository/"

  if (isSnapshot.value)
    Some("snapshots" at nexus + "maven-snapshots/")
  else
    Some("releases"  at nexus + "maven-releases/")
}

credentials += {if(isStaging) Credentials(Path.userHome / ".ivy2" / ".credentialsTest") else Credentials(Path.userHome / ".ivy2" / ".credentials")}

