package com.spartaglobal.musicapiproject.entities;

import javax.persistence.*;

@Entity
@Table(name = "contenttypes")
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "typeID", nullable = false)
    private Integer id;

    public ContentType(Integer id) {
        this.id = id;
    }

    public ContentType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}