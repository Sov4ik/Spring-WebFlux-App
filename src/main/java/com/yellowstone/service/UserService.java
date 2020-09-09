package com.yellowstone.service;

import com.yellowstone.model.Usr;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public interface UserService {

     Mono<Usr> updateUser(final  Usr usr);

     Mono<Usr> createUser(final Usr usr);

     Flux<Usr> getAllUser();

     Mono<Void> deleteUser(final UUID id);

     Mono<Usr> delete(UUID id);
}
