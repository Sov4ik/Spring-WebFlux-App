package com.yellowstone.service;

import com.yellowstone.model.Usr;
import com.yellowstone.repository.UserRepository;
import com.yellowstone.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceImplTest {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void updateUser() {
        Usr user = new Usr();
        user.setId(UUID.randomUUID());
        user.setName("test");
        user.setEmail("email");
        user.setPassword("test");
        user.setAsNew();

        Mono<Usr> mono = userRepository.save(user)
                .flatMap(usr->{
                    usr.setEmail("test2");
                    user.setEmail("test2");
                    return userService.updateUser(usr).flatMap(usr1 -> Mono.just(usr1.setAsNew()));
                })
                .doFinally(signalType ->  userRepository.delete(user).subscribe());

        StepVerifier
                .create(mono)
                .expectNext(user)
                .expectComplete()
                .verify();

        userRepository.delete(user).subscribe();
    }


    @Test
    void createUser() {

        Usr user = new Usr();
        user.setId(UUID.randomUUID());
        user.setName("test");
        user.setEmail("email");
        user.setPassword("test");
        user.setAsNew();

        Mono<Usr> mono = userRepository
                .save(user)
                .flatMap(usr -> Mono.just(usr.setAsNew()))
                .doFinally(signalType ->  userRepository.delete(user).subscribe());

        StepVerifier
                .create(mono)
                .expectNext(user)
                .expectComplete()
                .verify();

        userRepository.delete(user).subscribe();

    }

    @Test
    void deleteUser() {

        Usr user = new Usr();
        user.setId(UUID.randomUUID());
        user.setName("test");
        user.setEmail("email");
        user.setPassword("test");
        user.setAsNew();

        Mono<Usr> mono = userRepository
                .save(user)
                .then(userRepository
                        .delete(user)
                        .then(userRepository
                                .findById(user.getId())
                                .switchIfEmpty(Mono.empty())))
                .doFinally(signalType ->  userRepository.delete(user).subscribe());

        StepVerifier
                .create(mono)
                .expectComplete()
                .verify();

        userRepository.delete(user).subscribe();

    }


}
