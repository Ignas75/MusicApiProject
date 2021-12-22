package com.spartaglobal.musicapiproject.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "globaltrackpopularity")
public class GlobalTrackPopularity {
    @Id
    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Column(name = "Popularity", nullable = false)
    private Long popularity;

    public Long getPopularity() {
        return popularity;
    }

    public String getName() {
        return name;
    }
}