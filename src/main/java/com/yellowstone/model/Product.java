package com.yellowstone.model;

import com.yellowstone.annotation.AutoCreate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Data
@EqualsAndHashCode
@ToString
@AutoCreate
public class Product implements Persistable<UUID> {

    @Id
    private UUID id;
    private String description;
    private Double price;

    @Transient
    private boolean newProduct;

    @Override
    @Transient
    public boolean isNew() {
        return this.newProduct || id == null;
    }

    public Product setAsNew() {
        this.newProduct = true;
        return this;
    }
}
