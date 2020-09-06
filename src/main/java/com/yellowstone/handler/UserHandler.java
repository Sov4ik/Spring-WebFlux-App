package com.yellowstone.handler;

import com.yellowstone.model.Usr;
import com.yellowstone.service.UserService;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Component
public class UserHandler {

    private final UserService userService;

    private final BiFunction<Publisher<?>, Class<?>, Mono<ServerResponse>> defaultReadResponse;

    private final BiFunction<Publisher<?>, String, Mono<ServerResponse>> defaultWriteResponse;

    public UserHandler(UserService userService,
                       BiFunction<Publisher<?>, Class<?>, Mono<ServerResponse>> defaultReadResponse,
                       BiFunction<Publisher<?>, String, Mono<ServerResponse>> defaultWriteResponse) {
        this.userService = userService;
        this.defaultReadResponse = defaultReadResponse;
        this.defaultWriteResponse = defaultWriteResponse;
    }

    public Mono<ServerResponse> createUser(ServerRequest request){
        Flux<Usr> flux = request
                .bodyToFlux(Usr.class)
                .flatMap(userService::updateUser);

        return defaultReadResponse.apply(flux, Usr.class);
    }
}
