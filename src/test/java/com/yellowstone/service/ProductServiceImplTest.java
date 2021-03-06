package com.yellowstone.service;

import com.yellowstone.model.Product;
import com.yellowstone.repository.ProductRepository;
import com.yellowstone.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void createProduct() {

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setDescription("some description");
        product.setPrice(50.0);
        product.setAsNew();

        Mono<Product> productMono = productServiceImpl
                .createProduct(product)
                .then(productRepository
                        .findById(product.getId())
                        .flatMap(product1 -> {
                                    product1.setAsNew();
                                    return Mono.just(product1);
                        })
                )
                .doFinally(signalType -> productRepository.deleteById(product.getId()).subscribe());

        StepVerifier
                .create(productMono)
                .expectNext(product)
                .expectComplete()
                .verify();

        productRepository.deleteById(product.getId()).subscribe();

    }

    @Test
    @Disabled
    void getAllProducts() {

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setDescription("some description");
        product.setPrice(50.0);
        product.setAsNew();

        Flux<Product> productFlux = productRepository
                .save(product)
                .thenMany(productServiceImpl.getAllProducts())
                .doFinally(signalType -> productRepository.deleteById(product.getId()).subscribe());

        StepVerifier
                .create(productFlux)
                .expectNext()
                .expectComplete()
                .verify();

        productRepository.deleteById(product.getId()).subscribe();

    }

    @Test
    void updateProduct() {

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setDescription("some description");
        product.setPrice(50.0);
        product.setAsNew();

        Mono<Product> productMono = productRepository
                .save(product)
                .flatMap(product1 -> {
                    product1.setPrice(60.0);
                    product.setPrice(60.0);
                    return productServiceImpl
                                .updateProduct(product1)
                                .flatMap(product2 -> {
                                    product2.setAsNew();
                                    return Mono.just(product2);
                                });
                })
                .doFinally(signalType -> productRepository.deleteById(product.getId()).subscribe());

        StepVerifier
                .create(productMono)
                .expectNext(product)
                .expectComplete()
                .verify();

        productRepository.deleteById(product.getId()).subscribe();
    }

    @Test
    void deleteProduct() {

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setDescription("some description");
        product.setPrice(50.0);
        product.setAsNew();

        Mono<Product> productMono = productRepository
                .save(product)
                .flatMap(product1 -> productRepository
                        .delete(product1))
                .then(productRepository
                        .findById(product.getId())
                        .switchIfEmpty(Mono.empty()))
                .doFinally(signalType -> productRepository.deleteById(product.getId()).subscribe());

        StepVerifier
                .create(productMono)
                .verifyComplete();

    }

}
