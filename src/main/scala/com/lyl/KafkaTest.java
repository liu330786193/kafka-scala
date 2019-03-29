package com.lyl;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lyl
 * @Description
 * @Date 2019-03-18 15:45
 */
public class KafkaTest {

    public static void main(String[] args) {
        String zkQuorum = "localhost:2181";
        String group = "test-group";
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("KafkaInput");
        // Create a StreamingContext with a 1 second batch size
        JavaStreamingContext jssc = new JavaStreamingContext(conf, new Duration(1000));
        Map<String, Integer> topics = new HashMap<String, Integer>();
        topics.put("test", 1);
        JavaPairDStream<String, String> input = KafkaUtils.createStream(jssc, zkQuorum, group, topics);
        input.print();
        System.out.println("百里放羊");
        // start our streaming context and wait for it to "finish"
        /*jssc.start();
        // Wait for 10 seconds then exit. To run forever call without a timeout
        jssc.awaitTermination(10000);
        // Stop the streaming context
        jssc.stop();*/
    }

}
