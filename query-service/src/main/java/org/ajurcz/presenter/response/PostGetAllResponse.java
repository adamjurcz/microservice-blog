package org.ajurcz.presenter.response;

import org.ajurcz.core.domain.PostDto;

import java.util.List;

public record PostGetAllResponse(List<PostDto> postDtos) {
}
