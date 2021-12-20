package com.spartaglobal.musicapiproject.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "playlisttrack")
public class Playlisttrack {
    @EmbeddedId
    private PlaylisttrackId id;

    public PlaylisttrackId getId() {
        return id;
    }

    public void setId(PlaylisttrackId id) {
        this.id = id;
    }
}