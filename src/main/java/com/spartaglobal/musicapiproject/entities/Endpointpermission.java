package com.spartaglobal.musicapiproject.entities;

import javax.persistence.*;

@Entity
@Table(name = "endpointpermissions")
public class Endpointpermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowID", nullable = false)
    private Integer id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "isForCustomer")
    private Boolean isForCustomer;

    @Column(name = "isForStaff")
    private Boolean isForStaff;

    @Column(name = "isForAdmins")
    private Boolean isForAdmins;

    public Boolean getIsForAdmins() {
        return isForAdmins;
    }

    public void setIsForAdmins(Boolean isForAdmins) {
        this.isForAdmins = isForAdmins;
    }

    public Boolean getIsForStaff() {
        return isForStaff;
    }

    public void setIsForStaff(Boolean isForStaff) {
        this.isForStaff = isForStaff;
    }

    public Boolean getIsForCustomer() {
        return isForCustomer;
    }

    public void setIsForCustomer(Boolean isForCustomer) {
        this.isForCustomer = isForCustomer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}