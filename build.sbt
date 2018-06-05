import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.herokuapp.navneetgupta",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "akka-stream",
    libraryDependencies ++= {
    	val akkaVersion = "2.5.12"
    	Seq(
    		"org.scalatest" %% "scalatest" % "3.0.1" % "test",
	        "com.typesafe.akka" %% "akka-actor" % akkaVersion,
	        "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
	        "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
	        "com.typesafe.akka" %% "akka-stream" % akkaVersion,
	        "ch.qos.logback" % "logback-classic" % "1.2.3"
    	)
    }
  )
