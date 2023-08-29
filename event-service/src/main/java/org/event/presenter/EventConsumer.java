package org.event.presenter;

import org.ajurcz.event.domain.CommentDto;
import org.ajurcz.event.domain.CommentVerifiedDto;
import org.ajurcz.event.domain.Event;
import org.ajurcz.event.domain.PostDto;
import org.springframework.kafka.annotation.KafkaListener;


public interface EventConsumer {
    @KafkaListener(topics = "${newCommentary.consumer.topic}", groupId = "event-commentDto-consumer")
    void newCommentsListener(CommentDto commentDto);

    @KafkaListener(topics = "${newPost.consumer.topic}", groupId = "event-postDto-consumer")
    void newPostsListener(PostDto postDto);

    @KafkaListener(topics = "${newVerifiedCommentary.consumer.topic}", groupId = "event-verifiedCommentDto-consumer")
    void newVerifiedCommentaryListener(CommentVerifiedDto commentVerifiedDto);
}
