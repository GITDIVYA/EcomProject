package com.it.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.it.entities.CategaryEntity;

@Repository
public interface CategoryRepository extends MongoRepository<CategaryEntity, Long> {
	Optional<CategaryEntity> findByCmpName(String cname);
	Optional<CategaryEntity> findByName(String name);
	Optional<CategaryEntity> deleteByName(String cname);

}
