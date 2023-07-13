package org.ajurcz.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ajurcz.core.domain.CommentDto;
import org.ajurcz.core.domain.Commentary;
import org.ajurcz.core.domain.Event;

import org.ajurcz.presenter.requests.CommentaryRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CommentaryService {
    private List<Commentary> commentaries;
    private List<Integer> postIds;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CommentaryService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.commentaries = new ArrayList<>();
        this.postIds = new ArrayList<>();
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Commentary createCommentary(Integer postId,
                                CommentaryRequest commentaryRequest){
        boolean isPresent = postIds.stream().anyMatch(postId::equals);
        if(isPresent){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        Integer newCommentaryId;
        if(!commentaries.isEmpty()){
            newCommentaryId = commentaries.stream().max(Comparator.comparing(Commentary::getId)).get().getId() + 1;
        }
        else{
            newCommentaryId = 0;
        }
        Commentary commentary = new Commentary(newCommentaryId,
                commentaryRequest.content(), postId);
        commentaries.add(commentary);

        CommentDto commentDto = new CommentDto(commentary.getId(), commentary.getContent(), commentary.getPostId());
        Event commentCreatedEvent = new Event(commentDto.getClass().getName(), commentDto);
        HttpEntity<Event> request = new HttpEntity<>(commentCreatedEvent);
        String orchestratorUrl = "http://localhost:8083/api/v1/events";
        restTemplate
                .exchange(orchestratorUrl, HttpMethod.POST, request, Void.class);

        return commentary;
    }
}
