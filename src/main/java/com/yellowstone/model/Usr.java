package com.yellowstone.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.util.UUID;


@Data
public class Usr implements Persistable<UUID> {

    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;

    @Transient
    private boolean newUser;

    @Override
    @Transient
    public boolean isNew() {
        return this.newUser || id == null;
    }

    public Usr setAsNew() {
        this.newUser = true;
        return this;
    }
}
