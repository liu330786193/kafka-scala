//package com.lyl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.lyl.trace.DefaultTraceSegment;
//
///**
// * @Author lyl
// * @Description
// * @Date 2019-03-19 16:12
// */
//public class Json {
//
//    public static void main(String[] args) {
//
//        String str = "{\"applicationId\":\"lyl\",\"applicationInstanceId\":\"10.0.7.153:lyl\",\"ignore\":false,\"ip\":\"10.0.7.153\",\"rgts\":[\"1007153.218.15529815103290331\"],\"singleSpanSegment\":true,\"sizeLimited\":false,\"spans\":[{\"componentId\":2,\"endTime\":1552981515336,\"entry\":false,\"errorOccurred\":false,\"exit\":true,\"layer\":\"HTTP\",\"operationName\":\"/v1/event/list\",\"parentSpanId\":-1,\"peer\":\"consul.xiaomai5.com:8500\",\"spanId\":0,\"startTime\":1552981510329,\"tags\":[{\"key\":\"url\",\"value\":\"http://consul.xiaomai5.com:8500/v1/event/list?wait=5s&index=1\"},{\"key\":\"http.method\",\"value\":\"GET\"},{\"key\":\"status_code\",\"value\":\"200\"}],\"time\":5007}],\"traceSegmentId\":\"1007153.218.15529815103290330\"}";
//
//
//        final DefaultTraceSegment defaultTraceSegment = JSONObject.parseObject(str, DefaultTraceSegment.class);
//
//
//    }
//
//}
