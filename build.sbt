name := "dsl_view_poc"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "Hadoop Releases" at "https://repository.cloudera.com/content/repositories/releases/"
)

libraryDependencies ++= {
  Seq(
    "org.apache.spark" %% "spark-core" % "2.1.0",
    "org.apache.spark" %% "spark-streaming" % "2.1.0",
    "org.apache.hbase" % "hbase-common" % "1.2.1",
    "org.apache.hbase" % "hbase-client" % "1.2.1",
    "org.apache.hbase" % "hbase-server" % "1.2.1",
    "eu.unicredit" %% "hbase-rdd" % "0.7.1",
    "com.google.guava" % "guava" % "15.0",
    "org.apache.hadoop" % "hadoop-common" % "2.6.0",
    "org.apache.hadoop" % "hadoop-mapred" % "0.22.0",
    "org.apache.hbase" % "hbase-common" % "1.0.0",
    "org.apache.hbase" % "hbase-client" % "1.0.0",
    "org.apache.parquet" % "parquet-avro" % "1.9.0",
    "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.4",
  "com.thoughtworks.xstream" % "xstream" % "1.4.9"
  )
}

dependencyOverrides += "com.google.guava" % "guava" % "15.0"


assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case PathList("com", "google", xs @ _*) => MergeStrategy.first
  case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
  case "about.html" => MergeStrategy.rename
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}