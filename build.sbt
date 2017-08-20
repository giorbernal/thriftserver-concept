name := "thriftserver-concept"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.1.0",
  "org.apache.spark" %% "spark-sql" % "2.1.0",
  "org.apache.spark" % "spark-hive_2.11" % "2.1.0" % "provided",
  "org.apache.spark" % "spark-hive-thriftserver_2.11" % "2.1.0",
  "au.com.bytecode" % "opencsv" % "2.4"
)
