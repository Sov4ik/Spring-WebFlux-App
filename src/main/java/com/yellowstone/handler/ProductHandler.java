package com.yellowstone.handler;


import com.yellowstone.model.Product;
import com.yellowstone.service.ProductService;
import com.yellowstone.util.DefaultResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class ProductHandler {

    private final ProductService productService;

    private static final String PRODUCT = "/product";

    private final DefaultResponse defaultResponse;

    public ProductHandler(ProductService productService,
                          DefaultResponse defaultResponse) {
        this.productService = productService;
        this.defaultResponse = defaultResponse;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Flux<Product> flux = request
                .bodyToFlux(Product.class)
                .flatMap(this.productService::updateProduct);
        return defaultResponse.defaultWriteResponse().apply(flux, PRODUCT);
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return defaultResponse.defaultReadResponse().apply(this.productService.getAllProducts(), Product.class);
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        return defaultResponse.defaultReadResponse().apply(this.productService.delete(id(request)), Product.class);
    }

    public Mono<ServerResponse> autoCreate(ServerRequest request){
        return defaultResponse.defaultReadResponse().apply(
                this.productService
                    .autoCreateProduct()
                    .flatMap(Mono::just), Product.class);
    }

    private static UUID id(ServerRequest request) {
        return UUID.fromString(request.pathVariable("id"));
    }
}
