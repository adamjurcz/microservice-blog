package org.ajurcz.event.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajurcz.event.domain.Event;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class EventDeserializer implements Deserializer<Event> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Event deserialize(String s, byte[] data) {
        try{
            if(data==null){
                return null;
            }
            return objectMapper.readValue(new String(data, "UTF-8"), Event.class);
        }
        catch (Exception e){
            throw new SerializationException("Error when deserializing byte[] to Event");
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {
    }
}
