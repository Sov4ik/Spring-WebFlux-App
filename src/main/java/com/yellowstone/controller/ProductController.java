package com.yellowstone.controller;


import com.yellowstone.model.Product;
import com.yellowstone.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * Controller version
 *
 */

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public Flux<Product> getAll() {
        return this.productService.getAllProducts();
    }

    @PostMapping("/save")
    public Mono<Product> updateProduct(@RequestBody Product product) {
        return Objects.isNull(product.getId()) ?
                this.productService.createProduct(product) :
                this.productService.updateProduct(product);
    }

    @GetMapping("/auto-create")
    public Mono<Product> autoProduct(){
        return productService.autoCreateProduct();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable UUID id) {
        return this.productService.deleteProduct(id);
    }

}
