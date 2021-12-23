package com.spartaglobal.musicapiproject.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "album_discount", indexes = {
        @Index(name = "album_id", columnList = "album_id")
})
public class AlbumDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_discount_id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @Column(name = "discount", nullable = false)
    private Integer discount;

    @Column(name = "dateCreated", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "lastValidDay", nullable = false)
    private LocalDate lastValidDay;

    public LocalDate getLastValidDay() {
        return lastValidDay;
    }

    public void setLastValidDay(LocalDate lastValidDay) {
        this.lastValidDay = lastValidDay;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}