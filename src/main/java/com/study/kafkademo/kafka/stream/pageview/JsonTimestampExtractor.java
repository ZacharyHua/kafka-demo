package com.study.kafkademo.kafka.stream.pageview;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

/**
 * A timestamp extractor implementation that tries to extract event time from
 * the "timestamp" field in the Json formatted message.
 * @author 小呆呆
 * @create 2019-07-01 23:06
 **/
public class JsonTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(final ConsumerRecord<Object, Object> record, final long previousTimestamp) {
        if (record.value() instanceof PageViewTypedDemo.PageView) {
            return ((PageViewTypedDemo.PageView) record.value()).timestamp;
        }

        if (record.value() instanceof PageViewTypedDemo.UserProfile) {
            return ((PageViewTypedDemo.UserProfile) record.value()).timestamp;
        }

        if (record.value() instanceof JsonNode) {
            return ((JsonNode) record.value()).get("timestamp").longValue();
        }

        throw new IllegalArgumentException("JsonTimestampExtractor cannot recognize the record value " + record.value());
    }
}
