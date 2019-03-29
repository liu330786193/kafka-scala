package com.lyl

import com.timevale.cat.api.trace.DefaultTraceSegment
import io.protostuff.ProtobufIOUtil
import io.protostuff.runtime.RuntimeSchema
import kafka.serializer.Decoder
import kafka.utils.VerifiableProperties

class ProtobufDecoder(props: VerifiableProperties = null) extends Decoder[DefaultTraceSegment]{

  def fromBytes(bytes: Array[Byte]): DefaultTraceSegment = {
    val clazz = classOf[DefaultTraceSegment];
    val schema = RuntimeSchema.getSchema(clazz);
    val jvm = clazz.newInstance();
    ProtobufIOUtil.mergeFrom(bytes, jvm, schema);
    jvm
  }
}
