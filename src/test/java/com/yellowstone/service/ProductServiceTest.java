package com.yellowstone.service;

import com.yellowstone.model.Product;
import com.yellowstone.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    public void cleanUp(){
        productRepository.deleteById(2).subscribe();
    }


    @Test
    void createProduct() {

        Product product = new Product();
        product.setId(2);
        product.setDescription("some description");
        product.setPrice(50.0);
        product.setAsNew();

        Mono<Product> productMono = productService
                .createProduct(product)
                .then(productRepository
                        .findById(2)
                        .flatMap(product1 -> {
                                    product1.setAsNew();
                                    return Mono.just(product1);
                        })
                )
                .doFinally(signalType -> productRepository.deleteById(2).subscribe());

        StepVerifier
                .create(productMono)
                .expectNext(product)
                .expectComplete()
                .verify();

        cleanUp();

    }

    @Test
    void getAllProducts() {

        Product product = new Product();
        product.setId(2);
        product.setDescription("some description");
        product.setPrice(50.0);
        product.setAsNew();

        Flux<Product> productFlux = productRepository
                .save(product)
                .thenMany(productService.getAllProducts())
                .doFinally(signalType -> productRepository.deleteById(2).subscribe());

        StepVerifier
                .create(productFlux)
                .expectNextMatches(product1 -> {
                    product1.setAsNew();
                    return product1.equals(product);
                })
                .expectComplete()
                .verify();

        cleanUp();

    }

    @Test
    void updateProduct() {

        Product product = new Product();
        product.setId(2);
        product.setDescription("some description");
        product.setPrice(50.0);
        product.setAsNew();

        Mono<Product> productMono = productRepository
                .save(product)
                .flatMap(product1 -> {
                    product1.setPrice(60.0);
                    product.setPrice(60.0);
                    return productService
                                .updateProduct(product1)
                                .flatMap(product2 -> {
                                    product2.setAsNew();
                                    return Mono.just(product2);
                                });
                })
                .doFinally(signalType -> productRepository.deleteById(2).subscribe());

        StepVerifier
                .create(productMono)
                .expectNext(product)
                .expectComplete()
                .verify();

        cleanUp();
    }

    @Test
    void deleteProduct() {

        Product product = new Product();
        product.setId(2);
        product.setDescription("some description");
        product.setPrice(50.0);
        product.setAsNew();

        Mono<Product> productMono = productRepository
                .save(product)
                .flatMap(product1 -> productRepository
                        .delete(product1))
                .then(productRepository
                        .findById(2)
                        .switchIfEmpty(Mono.empty()))
                .doFinally(signalType -> productRepository.deleteById(2).subscribe());

        StepVerifier
                .create(productMono)
                .verifyComplete();

    }

}
