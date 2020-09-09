package com.yellowstone.util;

import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.BiFunction;

@Component
public class DefaultResponse {

    public BiFunction<Publisher<?>, Class<?>, Mono<ServerResponse>> defaultReadResponse(){
        return (publisher, Class) -> ServerResponse.ok().body(publisher, Class);
    }

    public BiFunction<Publisher<?>, String, Mono<ServerResponse>> defaultWriteResponse(){
        return (publisher, url) ->
                Mono
                    .from(publisher)
                    .flatMap(p -> ServerResponse
                            .created(URI.create(url))
                            .contentType(MediaType.APPLICATION_JSON)
                            .build()
                    );
    }
}
