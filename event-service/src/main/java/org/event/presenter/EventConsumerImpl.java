package org.event.presenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajurcz.event.domain.CommentDto;
import org.ajurcz.event.domain.CommentVerifiedDto;
import org.ajurcz.event.domain.Event;
import org.event.core.usecase.AddToEventStoreUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

@Component
public class EventConsumerImpl implements EventConsumer{
    Logger logger = LoggerFactory.getLogger(EventConsumerImpl.class);

    private final AddToEventStoreUseCase addToEventStoreUseCase;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, Event> kafkaTemplate;

    public EventConsumerImpl(AddToEventStoreUseCase addToEventStoreUseCase, ObjectMapper objectMapper, KafkaTemplate<String, Event> kafkaTemplate) {
        this.addToEventStoreUseCase = addToEventStoreUseCase;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void eventListener(Event event) {
        String commentNotVerifiedTopic = "comment_not_verified_topic";
        String badCommentsTopic = "bad_comments_topic";
        String validTopic = "validated_events_topic";

        logger.info("dostalismy event");

        addToEventStoreUseCase.execute(new AddToEventStoreUseCase.Input(event));
        try {
            Class<?> clazz = Class.forName(event.getClassName());
            Object object = objectMapper.convertValue(event.getObject(), clazz);
            if (object instanceof CommentDto) {
                logger.info("przekierowujemy komentarz na verify");
                kafkaTemplate.send(commentNotVerifiedTopic, event);
            }
            else if (object instanceof CommentVerifiedDto commentVerifiedDto){
                if(commentVerifiedDto.isValid()) {
                    logger.info("przekierowujemy commentverified na queries");
                    kafkaTemplate.send(validTopic, event);
                }
                else{
                    logger.info("przekierowujemy badcomment na commentaries");
                    kafkaTemplate.send(badCommentsTopic, event);
                }
            }
            else{
                logger.info("przekierowujemy post do queries");
                kafkaTemplate.send(validTopic, event);
            }
        }
        catch (ClassNotFoundException | ResourceAccessException e){
            ///TODO
        }

    }
}
