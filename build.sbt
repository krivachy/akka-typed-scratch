name := "akka-typed-scratch"

version := "0.1"

scalaVersion := "2.12.4"

val akkaV = "2.5.7"

libraryDependencies := Seq(
  "com.typesafe.akka" %% "akka-typed" % akkaV,
  "com.typesafe.akka" %% "akka-typed-testkit" % akkaV,
)
