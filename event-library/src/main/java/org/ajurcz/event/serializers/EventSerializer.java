package org.ajurcz.event.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajurcz.event.domain.Event;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class EventSerializer implements Serializer<Event> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, Event event) {
        try{
            if(event==null){
                return null;
            }
            return objectMapper.writeValueAsBytes(event);
        }
        catch (Exception e){
            throw new SerializationException("Error when serializing event to byte[]");
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {
    }
}
