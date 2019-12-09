name := "cayenne_jasyncsql"

version := "0.1"

scalaVersion := "2.13.1"


libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.14.2" % Test withSources(),
  "com.storm-enroute" %% "scalameter" % "0.19" % Test withSources(),
  "org.apache.cayenne" % "cayenne-server" % "4.1.RC2" withSources(),
  "com.github.jasync-sql" % "jasync-mysql" % "1.0.12" withSources()
)