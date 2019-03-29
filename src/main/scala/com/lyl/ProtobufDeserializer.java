//package com.lyl;
//
//import com.lyl.trace.DefaultTraceSegment;
//import io.protostuff.ProtobufIOUtil;
//import io.protostuff.Schema;
//import io.protostuff.runtime.RuntimeSchema;
//import kafka.serializer.Decoder;
//public class ProtobufDeserializer implements Decoder {
//
//
//    @Override
//    public DefaultTraceSegment fromBytes(byte[] data) {
//        if (data == null){
//            return null;
//        }
//        Class clazz = DefaultTraceSegment.class;
//        Schema schema = RuntimeSchema.getSchema(clazz);
//        DefaultTraceSegment t = null;
//        try {
//            t = (DefaultTraceSegment) clazz.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ProtobufIOUtil.mergeFrom(data, t, schema);
//        return t;
//    }
//}
