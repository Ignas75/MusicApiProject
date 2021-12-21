package com.spartaglobal.musicapiproject.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "albumpopularitybycountry")
public class AlbumPopularityByCountry {
    @Id
    @Column(name = "BillingCountry", length = 40)
    private String billingCountry;

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

    public String getBillingCountry() {
        return billingCountry;
    }
}