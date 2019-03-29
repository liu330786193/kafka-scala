package com.lyl

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import com.timevale.cat.api.trace.DefaultTraceSegment
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.elasticsearch.spark.streaming.api.java.JavaEsSparkStreaming
import shapeless.record

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object MyScala {

  def main(args: Array[String]): Unit = {
    testKafkaConsumer()
  }

  def testKafkaConsumer() = {
    var brokers = "localhost:9092"
    val topics = "cat-agent-trace2"
    // Create context with 2 second batch interval
    @transient
    val sparkConf = new SparkConf()
      .setAppName("DirectKafkaWordCount1")
      .setMaster("local[2]")
      .set("spark.executor.memory", "3g")

    //设置elasticsearch
    sparkConf.set("es.nodes", "localhost")
    sparkConf.set("es.port", "9200")
    sparkConf.set("es.index.auto.create", "true")
    //    val esSc = new SparkContext(sparkConf)

    @transient
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    // Create direct kafka stream with brokers and topics
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    val messages = KafkaUtils.createDirectStream[DefaultTraceSegment, DefaultTraceSegment, ProtobufDecoder, ProtobufDecoder](
      ssc, kafkaParams, topicsSet)

    //    var list = ListBuffer.empty[String];

    //    val dstrem = messages.map(record => JSON.toJSONString(record._2, SerializerFeature.DisableCircularReferenceDetect));
    @transient
    var sparkContext = ssc.sparkContext;
    /*val dstrem = messages.map(record => {
      val defaultTraceSegment = record._2;
//      val spans = defaultTraceSegment.getSpans;
//      if (spans.size() > 0){
//        val url =  spans.get(0).getOperationName;
//        val urlAccu = sparkContext.longAccumulator(url)
//        urlAccu.add(1)
//        println(urlAccu.value)
      JSON.toJSONString(record._2, SerializerFeature.DisableCircularReferenceDetect)
      });*/
    //    dstrem.print();

    val pairs = messages.map(url => (url._2.getSpans.get(0).getOperationName, 1))
    val urlCounts = pairs.reduceByKey(_ + _);
    urlCounts.print();


    //    sparkContext.

    //    JavaEsSparkStreaming.saveJsonToEs(dstrem, "cat/trace")

    //    JavaEsSparkStreaming.save(dstrem, "cat/trace/" + traceSegmentId)
    /*messages.foreachRDD(rdd => {
        val count = rdd.count();
        if(count != 0){
          rdd.foreach(r => {
            var defaultTraceSegment = r._2;
            val traceSegmentId = defaultTraceSegment.getTraceSegmentId;
            println(r._2.getTraceSegmentId)
//            if(list.size == (count)){
//              esSc.makeRDD(Seq(defaultTraceSegment)).saveToEs("cat/trace/" + traceSegmentId)
//            JavaEsSparkStreaming.saveJsonToEs(dstrem, "cat/trace/" + traceSegmentId)
//            }
          })
        }
      }*/
    //    )

    ssc.start()
    ssc.awaitTermination()

  }

  /*def testKafkaProducer() = {
    /*val sparkConf = new SparkConf().setAppName("DirectKafkaWordCountDemo")
    sparkConf.setMaster("local")
    val ssc = new StreamingContext(sparkConf, Seconds(3))*/
    var brokers = "localhost:9092"
    val topics = "topic-test"
    val messagesPerSec=1 //每秒发送几条信息
    val wordsPerMessage =4 //一条信息包括多少个单词
//    val topicSet = topics.split(",").toSet
    val props = new util.HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    while (true){
      (1 to messagesPerSec.toInt).foreach{mesageNum => {
          val str = (1 to wordsPerMessage.toInt)
            .map(x => scala.util.Random.nextInt(10).toString).mkString(" ")
          val message = new ProducerRecord[String, String](topics, null, str)
          producer.send(message)
          println(message)
        }
        Thread.sleep(3000)
      }
    }
  }

  def test() = {
    val conf = new SparkConf().setAppName("mySpark")
    conf.setMaster("local")
    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(List(1,2,3,4,5,6)).map(_*3)
    val mappedRDD = rdd.filter(_>10).collect()
    println(rdd.reduce(_+_))
    for (arg <- mappedRDD)
      print(arg + " ")
    println()
    print("math is work")
  }*/

}
