package com.yellowstone.repository;

import com.yellowstone.model.Usr;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<Usr, UUID> {
}
