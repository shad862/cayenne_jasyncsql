name := "cayenne_jasyncsql"

version := "0.1"

scalaVersion := "2.13.1"


libraryDependencies ++= Seq(
  "org.apache.cayenne" % "cayenne-server" % "4.1.RC2" withSources(),
  "com.github.jasync-sql" % "jasync-mysql" % "1.0.12" withSources(),
  "com.github.jasync-sql" % "jasync-common" % "1.0.12" withSources(),
  "mysql" % "mysql-connector-java" % "8.0.18" withSources(),
  "com.storm-enroute" %% "scalameter" % "0.19" % Test withSources(),
  "org.scalacheck" %% "scalacheck" % "1.14.2" % Test withSources(),
  "com.lihaoyi" %% "utest" % "0.7.1" % Test withSources(),
  "org.junit.jupiter" % "junit-jupiter-engine" % "5.5.2" % Test withSources(),
  "org.slf4j" % "slf4j-log4j12" % "1.7.29" % Test withSources()
)

testFrameworks += new TestFramework("utest.runner.Framework")
testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

logBuffered := false

parallelExecution in Test := false