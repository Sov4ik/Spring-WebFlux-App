package com.yellowstone.controller;


import com.yellowstone.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductsEndpointConfig {

    private static String PRODUCT = "/product";

    @Bean
    RouterFunction<ServerResponse> routesProduct(ProductHandler handler) {
        return route(GET(PRODUCT), handler::all)
                .andRoute(POST(PRODUCT), handler::create)
                .andRoute(DELETE(PRODUCT + "/{id}"), handler::deleteById)
                .andRoute(GET(PRODUCT + "/auto-create"), handler::autoCreate);
    }
}
