package com.spartaglobal.musicapiproject.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bulk_purchase_discount")
public class BulkPurchaseDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bulk_discount_id", nullable = false)
    private Integer id;

    @Column(name = "minimumPurchased")
    private Integer minimumPurchased;

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

    public Integer getMinimumPurchased() {
        return minimumPurchased;
    }

    public void setMinimumPurchased(Integer minimumPurchased) {
        this.minimumPurchased = minimumPurchased;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}