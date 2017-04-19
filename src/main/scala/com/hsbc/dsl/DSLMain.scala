package com.hsbc.dsl

import java.io.ByteArrayOutputStream

import com.hsbc.dsl.models.Person
import com.sksamuel.avro4s.AvroOutputStream
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{ CellUtil, HBaseConfiguration, TableName }

import scala.collection.JavaConverters._
import scala.io.Source

/**
  * Created by Bala on 17/04/2017.
  */
object DSLMain extends App {

  def printRow(result: Result) = {
    val cells = result.rawCells()
    print(Bytes.toString(result.getRow) + " : ")
    for (cell <- cells) {
      val col_name = Bytes.toString(CellUtil.cloneQualifier(cell))
      val col_value = Bytes.toString(CellUtil.cloneValue(cell))
      print("(%s,%s) ".format(col_name, col_value))
    }
    println()
  }

  val conf: Configuration = HBaseConfiguration.create()

  val ZOOKEEPER_QUORUM = "localhost"
  conf.set("hbase.zookeeper.quorum", ZOOKEEPER_QUORUM)
  conf.set("zookeeper.znode.parent", "/hbase-unsecure")

  val connection = ConnectionFactory.createConnection(conf)
  val table = connection.getTable(TableName.valueOf(Bytes.toBytes("test")))

  val xstream = new XStream(new DomDriver)
  val personString: String = Source.fromInputStream(getClass().getClassLoader().getResourceAsStream("person.xml")).mkString
  println(personString)
  xstream.alias("Person", classOf[Person])
  val msdPerson: Person = xstream.fromXML(personString).asInstanceOf[Person]

  val baos = new ByteArrayOutputStream()
  val output = AvroOutputStream.binary[Person](baos)
  output.write(msdPerson)
  output.close()
  val bytes = baos.toByteArray

  // Insert data into HBase
  var put = new Put(Bytes.toBytes("row1"))
  put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("d"), bytes)
  table.put(put)

  // Get the record
  println("Get Example:")
  var get = new Get(Bytes.toBytes("row1"))
  var result = table.get(get)
  printRow(result)

  //Scan the records
  println("\nScan Example:")
  var scan = table.getScanner(new Scan())
  scan.asScala.foreach(result => {
    printRow(result)
  })

  table.close()
  connection.close()

}
