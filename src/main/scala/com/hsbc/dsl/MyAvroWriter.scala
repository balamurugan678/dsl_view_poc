package com.hsbc.dsl

import java.io.{ ByteArrayOutputStream, File, IOException }

import org.apache.avro.file.{ DataFileReader, DataFileWriter }
import org.apache.avro.generic.{ GenericDatumReader, GenericDatumWriter, GenericRecord, GenericRecordBuilder }
import org.apache.avro.io.{ DatumWriter, EncoderFactory }
import org.apache.avro.SchemaBuilder
import org.apache.avro.specific.SpecificDatumWriter
import org.apache.hadoop.fs.Path
import org.apache.parquet.avro.{ AvroParquetReader, AvroParquetWriter }
//import parquet.avro.AvroParquetReader
import scala.util.control.Breaks.break

object MyAvroWriter {
  def main(args: Array[String]) {
    // Build a schema
    /*val schema = SchemaBuilder
      .record("person")
      .fields
      .name("name").`type`().stringType().noDefault()
      .name("ID").`type`().intType().noDefault()
      .endRecord

    // Build an object conforming to the schema
    val user1 = new GenericRecordBuilder(schema)
      .set("name", "Jeff")
      .set("ID", 1)
      .build

    // JSON encoding of the object (a single record)
    val writer = new GenericDatumWriter[GenericRecord](schema)
    val baos = new ByteArrayOutputStream
    val jsonEncoder = EncoderFactory.get.jsonEncoder(schema, baos)
    writer.write(user1, jsonEncoder)
    jsonEncoder.flush
    println("JSON encoded record: " + baos)

    // binary encoding of the object (a single record)
    baos.reset
    val binaryEncoder = EncoderFactory.get.binaryEncoder(baos, null)
    writer.write(user1, binaryEncoder)
    binaryEncoder.flush
    println("Binary encoded record: " + baos.toByteArray)

    // Build another object conforming to the schema
    val user2 = new GenericRecordBuilder(schema)
      .set("name", "Sam")
      .set("ID", 2)
      .build

    // Write both records to an Avro object container file
    val file = new File("users.avro")
    file.deleteOnExit
    val dataFileWriter = new DataFileWriter[GenericRecord](writer)
    dataFileWriter.create(schema, file)
    dataFileWriter.append(user1)
    dataFileWriter.append(user2)
    dataFileWriter.close

    // Read the records back from the file
    val datumReader = new GenericDatumReader[GenericRecord](schema)
    val dataFileReader = new DataFileReader[GenericRecord](file, datumReader)
    var user: GenericRecord = null;
    while (dataFileReader.hasNext) {
      user = dataFileReader.next(user)
      println("Read user from Avro file: " + user)
    }

    // Write both records to a Parquet file
    val tmp = File.createTempFile(getClass.getSimpleName, ".tmp")
    tmp.deleteOnExit
    tmp.delete
    val tmpParquetFile = new Path(tmp.getPath)
    val parquetWriter = new AvroParquetWriter[GenericRecord](tmpParquetFile, schema)
    parquetWriter.write(user1)
    parquetWriter.write(user2)
    parquetWriter.close

    // Read both records back from the Parquet file
    val parquetReader = new AvroParquetReader[GenericRecord](tmpParquetFile)
    while (true) {
      Option(parquetReader.read) match {
        case Some(matchedUser) => println("Read user from Parquet file: " + matchedUser)
        case None => println("Finished reading Parquet file"); break
      }
    }*/
    val emp1: Employee_Record = Employee_Record("Ram","12-21-2009", "Hadoop Developer", 25000.00)

    // Alternate constructor
    val emp2: Employee_Record = new Employee_Record("Bharath",
      "10- 20-2005",
      "Team Lead",
      75000.00)

    // Construct via builder
    /*val emp3: Employee_Record = Employee_Record
      .newBuilder()
      .setName("Charlie")
      .setJoiningDate("07-10-2002")
      .setRole("Project Manager")
      .setDept(null)
      .setSalary(125000.00.toFloat)
      .build()*/
    // Serialize emp1 and emp2 to disk
    val file: File = new File("employees.avro")
    val userDatumWriter: DatumWriter[Employee_Record] =
      new SpecificDatumWriter[Employee_Record](classOf[Employee_Record])
    val dataFileWriter: DataFileWriter[Employee_Record] =
      new DataFileWriter[Employee_Record](userDatumWriter)
    //dataFileWriter.create(emp1.getSchema, file)
    dataFileWriter.append(emp1)
    dataFileWriter.append(emp2)
    //dataFileWriter.append(emp3)
    dataFileWriter.close()
  }
}

case class Employee_Record(name:String, joiningDate:String, role:String,salary:Double)
