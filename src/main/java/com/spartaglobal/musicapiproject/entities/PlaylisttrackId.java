package com.spartaglobal.musicapiproject.entities;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlaylisttrackId implements Serializable {
    private static final long serialVersionUID = -457234971229415390L;
    @Column(name = "PlaylistId", nullable = false)
    private Integer playlistId;
    @Column(name = "TrackId", nullable = false)
    private Integer trackId;

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, trackId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlaylisttrackId entity = (PlaylisttrackId) o;
        return Objects.equals(this.playlistId, entity.playlistId) &&
                Objects.equals(this.trackId, entity.trackId);
    }
}