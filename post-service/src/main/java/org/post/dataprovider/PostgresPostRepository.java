package org.post.dataprovider;

import org.springframework.data.repository.CrudRepository;

public interface PostgresPostRepository extends CrudRepository<PostData, Integer> {
}
