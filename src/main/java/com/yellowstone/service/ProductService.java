package com.yellowstone.service;

import com.yellowstone.model.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public interface ProductService {

    Mono<Product> createProduct(final Product product);

    Flux<Product> getAllProducts();

    Mono<Product> autoCreateProduct();

    Mono<Product> updateProduct(final Product product);

    Mono<Void> deleteProduct(final UUID id);

    Mono<Product> delete(UUID id);
}
