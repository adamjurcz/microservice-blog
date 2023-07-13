package org.ajurcz.presenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.ajurcz.core.domain.Event;
import org.ajurcz.core.domain.PostDto;
import org.ajurcz.presenter.response.PostGetAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QueryController implements QueryResource{

    private final ObjectMapper objectMapper;

    List<PostDto> postDtos = new ArrayList<>();

    public QueryController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<Void> eventHandler(Event event) {
        try{
            Class<?> clazz = Class.forName(event.getClassName());
            Object object = objectMapper.convertValue(event.getObject(), clazz);
            if(object instanceof PostDto postDto){
                postDtos.add(postDto);
            }
        }
        catch (ClassNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostGetAllResponse> getPosts() {
        return new ResponseEntity<>(new PostGetAllResponse(postDtos), HttpStatus.OK);
    }
}
