package com.spartaglobal.musicapiproject.entities;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BasketId implements Serializable {
    private static final long serialVersionUID = 8560685857406951800L;
    @Column(name = "TrackId", nullable = false)
    private Integer trackId;
    @Column(name = "CustomerId", nullable = false)
    private Integer customerId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackId, customerId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BasketId entity = (BasketId) o;
        return Objects.equals(this.trackId, entity.trackId) &&
                Objects.equals(this.customerId, entity.customerId);
    }
}