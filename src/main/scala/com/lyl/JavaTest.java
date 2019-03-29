//package com.lyl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//
///**
// * @Author lyl
// * @Description
// * @Date 2019-03-18 14:34
// */
//public class JavaTest {
//
//    public static void main(String[] args) {
//        JavaSparkContext sc = new JavaSparkContext(
//                "local", "basicjoincsv", System.getenv("SPARK_HOME"), System.getenv("JARS"));
//        JavaRDD<String> jsonFile = sc.textFile("/Users/lyl/Desktop/1.txt");
//        JavaRDD<User> result = jsonFile.mapPartitions(new ParseJson()).filter(new LikesPandas());
//        JavaRDD<String> formatted = result.mapPartitions(new WriteJson());
//        formatted.saveAsTextFile("/Users/lyl/Desktop/json");
//    }
//
//
//    public static class User implements java.io.Serializable {
//        public String name;
//        public Integer age;
//    }
//
//    public static class ParseJson implements FlatMapFunction<Iterator<String>, User> {
//        public Iterable<User> call(Iterator<String> lines) throws Exception {
//            ArrayList<User> people = new ArrayList<User>();
//            ObjectMapper mapper = new ObjectMapper();
//            while (lines.hasNext()) {
//                String line = lines.next();
//                try {
//                    people.add(mapper.readValue(line, User.class));
//                } catch (Exception e) {
//                    // Skip invalid input
//                }
//            }
//            return people;
//        }
//    }
//
//    public static class LikesPandas implements Function<User, Boolean> {
//        public Boolean call(User user) {
//            return StringUtils.isNotEmpty(user.name);
//        }
//    }
//
//    public static class WriteJson implements FlatMapFunction<Iterator<User>, String> {
//        public Iterable<String> call(Iterator<User> people) throws Exception {
//            ArrayList<String> text = new ArrayList<String>();
//            ObjectMapper mapper = new ObjectMapper();
//            while (people.hasNext()) {
//                User user = people.next();
//                text.add(mapper.writeValueAsString(user));
//            }
//            return text;
//        }
//    }
//
//}
