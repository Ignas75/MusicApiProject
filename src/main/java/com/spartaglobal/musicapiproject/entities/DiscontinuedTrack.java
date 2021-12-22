package com.spartaglobal.musicapiproject.entities;

import javax.persistence.*;

@Entity
@Table(name = "discontinuedtrack", indexes = {
        @Index(name = "TrackId", columnList = "TrackId")
})
public class DiscontinuedTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiscontinuedID", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "TrackId")
    private Track trackId;

    public Track getTrackId() {
        return trackId;
    }

    public void setTrackId(Track trackId) {
        this.trackId = trackId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}