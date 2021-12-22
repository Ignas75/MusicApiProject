package com.spartaglobal.musicapiproject.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "globalalbumpopularity")
public class GlobalAlbumPopularity{
    @Id
    @Column(name = "Title", nullable = false, length = 160)
    private String title;

    @Column(name = "Popularity", nullable = false)
    private Long popularity;

    public Long getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }
}