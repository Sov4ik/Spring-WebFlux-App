package com.yellowstone.controller;

import com.yellowstone.handler.ProductHandler;
import com.yellowstone.handler.UserHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserEndpointConfig {

    private static String USER = "/user";

    @Bean
    RouterFunction<ServerResponse> routesUser(UserHandler handler) {
        return route(POST(USER), handler::createUser);
    }
}
