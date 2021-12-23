package com.spartaglobal.musicapiproject.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "basket")
public class Basket {
    @EmbeddedId
    private BasketId id;

    public BasketId getId() {
        return id;
    }

    public void setId(BasketId id) {
        this.id = id;
    }
}