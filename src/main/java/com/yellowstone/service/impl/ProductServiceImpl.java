package com.yellowstone.service.impl;


import com.yellowstone.factory.ProductFactory;
import com.yellowstone.model.Product;
import com.yellowstone.repository.ProductRepository;
import com.yellowstone.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductFactory productFactory;

    public ProductServiceImpl(ProductRepository repository, ProductFactory productFactory) {
        this.repository = repository;
        this.productFactory = productFactory;
    }

    public Mono<Product> createProduct(final Product product) {
        return this.repository
                .save(product);
    }

    public Flux<Product> getAllProducts() {
        return this.repository
                .findAll();
    }

    public Mono<Product> autoCreateProduct(){
        return this.repository.save(productFactory
                    .newProduct()
                    .setAsNew());
    }

    @Transactional
    public Mono<Product> updateProduct(final Product product) {
        return this.repository.findById(product.getId())
                .flatMap(p -> {
                    p.setDescription(product.getDescription());
                    p.setPrice(product.getPrice());
                    return this.repository.save(p);
                })
                .switchIfEmpty(this.repository.save(product.setAsNew()));
    }

    @Transactional
    public Mono<Void> deleteProduct(final UUID id) {
        return this.repository.findById(id)
                .flatMap(this.repository::delete);
    }

    @Transactional
    public Mono<Product> delete(UUID id) {
        return this.repository
                .findById(id)
                .flatMap(product -> this.repository.deleteById(product.getId()).thenReturn(product));
    }

}
