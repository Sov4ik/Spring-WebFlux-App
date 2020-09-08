package com.yellowstone.controller;

import com.yellowstone.model.Product;
import com.yellowstone.factory.ProductFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class TestController {

    private final ProductFactory productFactory;

    public TestController(ProductFactory productFactory) {
        this.productFactory = productFactory;
    }

    @GetMapping("/getSome")
    public Mono<Product> get(){
        return Mono.just(productFactory.newProduct());
    }

}
