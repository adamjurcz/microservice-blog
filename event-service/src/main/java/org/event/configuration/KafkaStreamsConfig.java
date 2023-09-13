package org.event.configuration;

import org.ajurcz.event.domain.CommentVerifiedDto;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Produced;
import org.event.core.usecase.AddToEventStoreUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamsConfig {

    @Value("${newVerifiedCommentary.consumer.topic}")
    private String newVerifiedCommentsConsumerTopicName;
    @Value("${badCommentary.producer.topic}")
    private String badCommentsProducerTopicName;
    @Value("${validCommentary.producer.topic}")
    private String validCommentsProducerTopicName;
    @Value("${bootstrap.server.url}")
    private String bootstrapServer;

    private final AddToEventStoreUseCase addToEventStoreUseCase;

    public KafkaStreamsConfig(AddToEventStoreUseCase addToEventStoreUseCase) {
        this.addToEventStoreUseCase = addToEventStoreUseCase;
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration(){
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "eventStreams");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KStream<String, CommentVerifiedDto> newVerifiedCommentaryPipeline(StreamsBuilder streamsBuilder){
        JsonSerde<CommentVerifiedDto> jsonSerde = new JsonSerde<>(CommentVerifiedDto.class);

        Predicate<String, CommentVerifiedDto> validComment = (key, dto) -> dto.isValid();
        Predicate<String, CommentVerifiedDto> invalidComment = (key, dto) -> !dto.isValid();

        KStream<String, CommentVerifiedDto> sourceStream = streamsBuilder
                .stream(newVerifiedCommentsConsumerTopicName, Consumed.with(Serdes.String(), jsonSerde));
        sourceStream = sourceStream.peek((key, dto) -> addToEventStoreUseCase.execute(new AddToEventStoreUseCase.Input(dto)));

        @SuppressWarnings("unchecked")
        KStream<String, CommentVerifiedDto>[] splitStream = sourceStream.branch(validComment, invalidComment);
        splitStream[0].to(validCommentsProducerTopicName, Produced.with(Serdes.String(), jsonSerde));
        splitStream[1].to(badCommentsProducerTopicName, Produced.with(Serdes.String(), jsonSerde));
        return sourceStream;
    }

}
