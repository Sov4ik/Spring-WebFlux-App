package com.yellowstone.handler;


import com.yellowstone.model.Product;
import com.yellowstone.service.ProductService;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Component
public class ProductHandler {

    private final ProductService productService;

    private static String PRODUCT = "/product";

    private final BiFunction<Publisher<?>, Class<?>, Mono<ServerResponse>> defaultReadResponse;

    private final BiFunction<Publisher<?>, String, Mono<ServerResponse>> defaultWriteResponse;

    public ProductHandler(ProductService productService,
                          BiFunction<Publisher<?>, Class<?>, Mono<ServerResponse>> defaultReadResponse,
                          BiFunction<Publisher<?>, String, Mono<ServerResponse>> defaultWriteResponse) {
        this.productService = productService;
        this.defaultReadResponse = defaultReadResponse;
        this.defaultWriteResponse = defaultWriteResponse;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Flux<Product> flux = request
                .bodyToFlux(Product.class)
                .flatMap(this.productService::updateProduct);
        return defaultWriteResponse.apply(flux, PRODUCT);
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return defaultReadResponse.apply(this.productService.getAllProducts(), Product.class);
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        return defaultReadResponse.apply(this.productService.delete(id(request)), Product.class);
    }

    private static int id(ServerRequest request) {
        return Integer.parseInt(request.pathVariable("id"));
    }
}
