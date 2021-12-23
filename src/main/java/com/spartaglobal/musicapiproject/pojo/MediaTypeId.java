package com.spartaglobal.musicapiproject.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MediaTypeId {

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}