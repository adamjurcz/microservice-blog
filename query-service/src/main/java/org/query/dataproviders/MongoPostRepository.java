package org.query.dataproviders;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPostRepository extends MongoRepository<PostData, Integer> {
}
