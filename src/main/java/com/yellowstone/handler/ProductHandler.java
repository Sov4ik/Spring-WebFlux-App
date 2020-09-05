package com.yellowstone.handler;


import com.yellowstone.model.Product;
import com.yellowstone.service.ProductService;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class ProductHandler {

    final ProductService productService;

    public ProductHandler(ProductService productService) {
        this.productService = productService;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Flux<Product> flux = request
                .bodyToFlux(Product.class)
                .flatMap(this.productService::updateProduct);
        return defaultWriteResponse(flux);
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return defaultReadResponse(this.productService.getAllProducts());
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        return defaultReadResponse(this.productService.delete(id(request)));
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<Product> products) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products, Product.class);
    }

    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Product> product) {
        return Mono
                .from(product)
                .flatMap(p -> ServerResponse
                        .created(URI.create("/product"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()
                );
    }

    private static int id(ServerRequest request) {
        return Integer.parseInt(request.pathVariable("id"));
    }
}
