package org.commentary.dataprovider;

import org.springframework.data.repository.CrudRepository;

public interface PostgresCommentaryRepository extends CrudRepository<CommentaryData, Integer> {
}
