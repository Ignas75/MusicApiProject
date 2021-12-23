package com.spartaglobal.musicapiproject.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlbumId {

    @JsonProperty("artistId")
    private ArtistId artistId;

    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    public ArtistId getArtistId() {
        return artistId;
    }

    public void setArtistId(ArtistId artistId) {
        this.artistId = artistId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}