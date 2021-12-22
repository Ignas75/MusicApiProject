package com.spartaglobal.musicapiproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerId", nullable = false)
    private Integer id;

    @Column(name = "FirstName", nullable = false, length = 40)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 20)
    private String lastName;

    @Column(name = "Company", length = 80)
    private String company;

    @Column(name = "Address", length = 70)
    private String address;

    @Column(name = "City", length = 40)
    private String city;

    @Column(name = "State", length = 40)
    private String state;

    @Column(name = "Country", length = 40)
    private String country;

    @Column(name = "PostalCode", length = 10)
    private String postalCode;

    @Column(name = "Phone", length = 24)
    private String phone;

    @Column(name = "Fax", length = 24)
    private String fax;

    @Column(name = "Email", nullable = false, length = 60)
    private String email;

    @ManyToOne
    @JoinColumn(name = "SupportRepId")
    private Employee supportRepId;

    public Employee getSupportRepId() {
        return supportRepId;
    }

    public void setSupportRepId(Employee supportRepId) {
        this.supportRepId = supportRepId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCustomer() {
        String data =
                "{\"id\":\""     + id +
                "\",\"firstName\":\""
                +firstName +
                "\",\"lastName\":\""
                + lastName+
                "\",\"company\":\""
                + company +
                "\",\"address\":\""
                + address +
                "\",\"city\":\""
                + city +
                "\",\"state\":\""
                + state +
                "\",\"country\":\""
                + country +
                "\",\"postalCode\":\""
                + postalCode +
                "\",\"phone\":\""
                + phone +
                "\",\"fax\":\""
                + fax +
                "\",\"email\":\""
                + email +
                "\",\"supportRepId\":\""
                + supportRepId.getId() +
                "\"}";
        return data;
    }
}