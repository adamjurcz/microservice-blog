package org.verify.configuration;

import org.ajurcz.event.domain.Event;
import org.ajurcz.event.serializers.EventSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {
    @Value("${bootstrap.server.url}")
    private String bootstrapServer;

    @Bean
    public Map<String, Object> kafkaProducerConfig(){
        String bootstrapServers = bootstrapServer;

        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EventSerializer.class.getName());
        return props;
    }

    @Bean
    public ProducerFactory<String, Event> eventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, Event> kafkaTemplate(){
        return new KafkaTemplate<>(eventProducerFactory());
    }
}
