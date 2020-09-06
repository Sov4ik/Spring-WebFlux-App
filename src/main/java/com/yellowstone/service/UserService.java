package com.yellowstone.service;

import com.yellowstone.model.Product;
import com.yellowstone.model.Usr;
import com.yellowstone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Mono<Usr> updateUser(final  Usr usr) {
        return this.userRepository.findById(usr.getId())
                .flatMap(p -> {
                    p.setName(usr.getName());
                    p.setEmail(usr.getEmail());
                    return this.userRepository.save(p);
                })
                .switchIfEmpty(this.userRepository.save(usr.setAsNew()));
    }
}
