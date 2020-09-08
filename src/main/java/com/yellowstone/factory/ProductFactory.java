package com.yellowstone.factory;

import com.yellowstone.model.Product;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

    @Bean
    public Product product(){
        return new Product();
    }

    @Lookup
    public Product newProduct(){
        return null;
    }

}
