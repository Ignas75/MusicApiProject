package com.spartaglobal.musicapiproject.entities;

import org.apache.catalina.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "track")
public class Track implements UserSearchable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TrackId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @ManyToOne
    @JoinColumn(name = "AlbumId")
    private Album albumId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "MediaTypeId", nullable = false)
    private Mediatype mediaTypeId;

    @ManyToOne
    @JoinColumn(name = "GenreId")
    private Genre genreId;

    @Column(name = "Composer", length = 220)
    private String composer;

    @Column(name = "Milliseconds", nullable = false)
    private Integer milliseconds;

    @Column(name = "Bytes")
    private Integer bytes;

    @Column(name = "UnitPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    public Integer getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(Integer milliseconds) {
        this.milliseconds = milliseconds;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public Genre getGenreId() {
        return genreId;
    }

    public void setGenreId(Genre genreId) {
        this.genreId = genreId;
    }

    public Mediatype getMediaTypeId() {
        return mediaTypeId;
    }

    public void setMediaTypeId(Mediatype mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
    }

    public Album getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Album albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String userSearch() {
        return name;
    }
}