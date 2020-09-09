package com.yellowstone.handler;

import com.yellowstone.model.Usr;
import com.yellowstone.service.UserService;
import com.yellowstone.util.DefaultResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private final UserService userService;

    private final DefaultResponse defaultResponse;

    public UserHandler(UserService userService,
                       DefaultResponse defaultResponse) {
        this.userService = userService;
        this.defaultResponse = defaultResponse;
    }

    public Mono<ServerResponse> createUser(ServerRequest request){
        Flux<Usr> flux = request
                .bodyToFlux(Usr.class)
                .flatMap(userService::updateUser);

        return defaultResponse.defaultReadResponse().apply(flux, Usr.class);
    }
}
