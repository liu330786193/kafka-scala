//package com.lyl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kafka.serializer.StringDecoder;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.streaming.Durations;
//import org.apache.spark.streaming.api.java.JavaDStream;
//import org.apache.spark.streaming.api.java.JavaPairInputDStream;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//import org.apache.spark.streaming.kafka.KafkaUtils;
//import scala.Tuple2;
//
//import java.util.*;
//
///**
// * @Author lyl
// * @Description
// * @Date 2019-03-18 17:13
// */
//public class KafkaScalaJvm {
//
//    public static void main(String[] args) {
//        SparkConf conf = new SparkConf().setAppName("wordcount").setMaster("local[2]");
//        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(5));
//
//// 首先要创建一份kafka参数map
//        Map<String, String> kafkaParams = new HashMap<String, String>();
//// 这里是不需要zookeeper节点,所以这里放broker.list
//        kafkaParams.put("metadata.broker.list", "localhost:9092");
//        kafkaParams.put("auto.offset.reset", "smallest");
//
//// 然后创建一个set,里面放入你要读取的Topic,可以并行读取多个topic
//        Set<String> topics = new HashSet<String>();
////        topics.add(TopicConfig.GC.topicName);
//        topics.add(TopicConfig.GC.topicName);
//
//        JavaPairInputDStream lines = KafkaUtils.createDirectStream(
//                jssc,
//                String.class, // key类型
//                String.class, // value类型
//                StringDecoder.class, // 解码器
//                ProtobufDeserializer.class,
//                kafkaParams,
//                topics);
//
//
////        lines.print();
//        JavaDStream<JvmDTO> result = lines.mapPartitions(new ParseJson()).filter(new LikesPandas());
//        result.foreachRDD(new Function<JavaRDD<JvmDTO>, Void>() {
//            @Override
//            public Void call(JavaRDD<JvmDTO> traceJavaRDD) throws Exception {
//                final List<JvmDTO> list = traceJavaRDD.collect();
//                if (CollectionUtils.isEmpty(list)){
//                    return null;
//                }
//                System.out.println(list.get(0).getData());
//                return null;
//            }
//        });
//        /*JavaDStream<String> words = lines.flatMap(new FlatMapFunction<Tuple2<String, String>, String>() {
//            private static final long serialVersionUID = 1L;
//            @Override
//            public Iterable<String> call(Tuple2<String, String> tuple) throws Exception {
//                return Arrays.asList(tuple._2.split(" "));
//            }
//        });
//
//        JavaPairDStream<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
//            private static final long serialVersionUID = 1L;
//            @Override
//            public Tuple2<String, Integer> call(String word) throws Exception {
//                return new Tuple2<String, Integer>(word, 1);
//            }
//        });
//
//        JavaPairDStream<String, Integer> wordcounts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
//            private static final long serialVersionUID = 1L;
//            @Override
//            public Integer call(Integer v1, Integer v2) throws Exception {
//                return v1 + v2;
//            }
//        });
//
//        wordcounts.print();*/
//        jssc.start();
//        jssc.awaitTermination();
//        jssc.close();
//    }
//
//    public static class User implements java.io.Serializable {
//        public String name;
//        public Integer age;
//    }
//
//    public static class ParseJson implements FlatMapFunction<Iterator<Tuple2<String, String>>, JvmDTO> {
//        public Iterable<JvmDTO> call(Iterator<Tuple2<String, String>> lines) throws Exception {
//            ArrayList<JvmDTO> people = new ArrayList<JvmDTO>();
//            ObjectMapper mapper = new ObjectMapper();
//            while (lines.hasNext()) {
//                final Tuple2<String, String> next = lines.next();
//                String value = next._2;
//                try {
//                    people.add(mapper.readValue(value, JvmDTO.class));
//                } catch (Exception e) {
//                    // Skip invalid input
//                }
//            }
//            return people;
//        }
//    }
//
//    public static class LikesPandas implements Function<JvmDTO, Boolean> {
//        public Boolean call(JvmDTO jvmDTO) {
//            return true;
//        }
//    }
//
//    public static class WriteJson implements FlatMapFunction<Iterator<JvmDTO>, String> {
//        public Iterable<String> call(Iterator<JvmDTO> people) throws Exception {
//            ArrayList<String> text = new ArrayList<String>();
//            ObjectMapper mapper = new ObjectMapper();
//            while (people.hasNext()) {
//                JvmDTO jvmDTO = people.next();
//                text.add(mapper.writeValueAsString(jvmDTO));
//            }
//            return text;
//        }
//    }
//
//}
