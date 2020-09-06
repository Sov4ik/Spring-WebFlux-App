package com.yellowstone.repository;

import com.yellowstone.model.Usr;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveCrudRepository<Usr, Long> {
}
