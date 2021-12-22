package com.spartaglobal.musicapiproject.entities;

import javax.persistence.*;

@Entity
@Table(name = "popularity", indexes = {
        @Index(name = "contentType", columnList = "contentType")
})
public class Popularity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RowID", nullable = false)
    private Integer id;

    @Column(name = "ContentTitle", nullable = false)
    private String contentTitle;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ContentType", nullable = false)
    private ContentType contentType;

    @Column(name = "NumberOfPurchases", nullable = false)
    private Integer numberOfPurchases;

    public Integer getNumberOfPurchases() {
        return numberOfPurchases;
    }

    public void setNumberOfPurchases(Integer numberOfPurchases) {
        this.numberOfPurchases = numberOfPurchases;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}