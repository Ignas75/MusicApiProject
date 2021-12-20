package com.spartaglobal.musicapiproject.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoiceline")
public class Invoiceline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceLineId", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "InvoiceId", nullable = false)
    private Invoice invoiceId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TrackId", nullable = false)
    private Track trackId;

    @Column(name = "UnitPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Track getTrackId() {
        return trackId;
    }

    public void setTrackId(Track trackId) {
        this.trackId = trackId;
    }

    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}