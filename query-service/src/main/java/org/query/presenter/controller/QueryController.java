package org.query.presenter.controller;

import org.query.core.domain.Post;
import org.query.core.usecase.GetAllPostsUseCase;
import org.query.presenter.response.PostGetAllResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryController implements QueryResource{

    private final GetAllPostsUseCase getAllPostsUseCase;

    public QueryController(GetAllPostsUseCase getAllPostsUseCase) {
        this.getAllPostsUseCase = getAllPostsUseCase;
    }

    @Override
    public ResponseEntity<PostGetAllResponse> getAllPosts() {
        List<Post> posts = getAllPostsUseCase.execute(new GetAllPostsUseCase.Input()).getPosts();
        return new ResponseEntity<>(new PostGetAllResponse(posts),HttpStatus.OK);
    }
}
