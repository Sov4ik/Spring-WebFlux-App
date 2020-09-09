package com.yellowstone.service.impl;

import com.yellowstone.model.Usr;
import com.yellowstone.repository.UserRepository;
import com.yellowstone.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Mono<Usr> updateUser(final  Usr usr) {

        return this.userRepository.findById(usr.getId())

                .flatMap(user -> {

                    user.setName(usr.getName());
                    user.setEmail(usr.getEmail());
                    user.setPassword(usr.getPassword());

                    return this.userRepository.save(user);
                })
                .switchIfEmpty(this.userRepository.save(usr.setAsNew()));
    }

    public Mono<Usr> createUser(final Usr usr) {
        return this.userRepository
                .save(usr);
    }

    public Flux<Usr> getAllUser() {
        return this.userRepository
                .findAll();
    }

    @Transactional
    public Mono<Void> deleteUser(final UUID id) {
        return this.userRepository.findById(id)
                .flatMap(this.userRepository::delete);
    }

    @Transactional
    public Mono<Usr> delete(UUID id) {
        return this.userRepository
                .findById(id)
                .flatMap(user -> this.userRepository.deleteById(user.getId()).thenReturn(user));
    }
}
