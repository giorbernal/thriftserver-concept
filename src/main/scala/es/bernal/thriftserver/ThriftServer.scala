package es.bernal.thriftserver

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.hive.thriftserver.HiveThriftServer2

/**
  * Created by bernal on 15/8/17.
  */
object ThriftServer extends App {

  val ss = SparkSession
    .builder()
    .appName("ThriftServer")
    .master("local[*]")
    .enableHiveSupport()
    .getOrCreate()

  val sc = ss.sparkContext;
  val sql = ss.sqlContext;
  sql.setConf("hive.server2.thrift.port", "10002");

  val delimiter = ",";

  // Load Data
  val data: RDD[String] = sc.textFile("/Users/bernal/Documents/incubator/Thriftserver-concept/src/main/resources/FL_insurance_sample.csv");

  val headers = data.first.split(delimiter);
  val schema = StructType(headers.map(h => StructField(h, StringType)))

  val rowRDD  = data.mapPartitionsWithIndex { (idx, iter) => if (idx == 0) iter.drop(1) else iter }.map(p => Row.fromSeq(p.split(delimiter)))

  // Spark SQL process
  val df: DataFrame = ss.createDataFrame(rowRDD, schema).cache

  //df.createOrReplaceTempView("insurance");
  df.write.saveAsTable("insurance");

  HiveThriftServer2.startWithContext(sql);

  println(">> Thrift Server started!");

  // Monitor system to stop de Job
  var isThereData: Boolean = true;
  while (isThereData) {
    Thread.sleep(10000);
    try {
      val dfTest = ss.sql("select * from insurance limit 1");
      dfTest.count();
    } catch {
      case e: Exception => {
        isThereData = false
        println("No data. Finishing Job and Thrift Server")
      }
    }
  }

  ss.stop();

}
