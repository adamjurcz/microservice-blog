package org.ajurcz.presenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.ajurcz.core.domain.*;
import org.ajurcz.core.usecase.GetAllPostsUseCase;
import org.ajurcz.core.usecase.HandleCommentEventUseCase;
import org.ajurcz.core.usecase.HandlePostEventUseCase;
import org.ajurcz.presenter.response.PostGetAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryController implements QueryResource{

    private final ObjectMapper objectMapper;

    private final HandleCommentEventUseCase handleCommentEventUseCase;

    private final HandlePostEventUseCase handlePostEventUseCase;

    private final GetAllPostsUseCase getAllPostsUseCase;

    public QueryController(ObjectMapper objectMapper,
                           HandleCommentEventUseCase handleCommentEventUseCase,
                           HandlePostEventUseCase handlePostEventUseCase,
                           GetAllPostsUseCase getAllPostsUseCase) {
        this.objectMapper = objectMapper;
        this.handleCommentEventUseCase = handleCommentEventUseCase;
        this.handlePostEventUseCase = handlePostEventUseCase;
        this.getAllPostsUseCase = getAllPostsUseCase;
    }

    @Override
    public ResponseEntity<Void> eventHandler(Event event) {
        try{
            Class<?> clazz = Class.forName(event.getClassName());
            Object object = objectMapper.convertValue(event.getObject(), clazz);
            if(object instanceof PostDto postDto){
                handlePostEventUseCase.execute(new HandlePostEventUseCase.Input(postDto.getId(),
                        postDto.getCreatorName(), postDto.getContent()));
            }
            if(object instanceof CommentVerifiedDto commentDto){
                handleCommentEventUseCase.execute(new HandleCommentEventUseCase.Input(commentDto.getId(),
                        commentDto.getContent(), commentDto.getPostId(), commentDto.isValid()));
            }
        }
        catch (ClassNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostGetAllResponse> getAllPosts() {
        List<Post> posts = getAllPostsUseCase.execute(new GetAllPostsUseCase.Input())
                .getPosts();
        return new ResponseEntity<>(new PostGetAllResponse(posts),HttpStatus.OK);
    }
}
