package com.spartaglobal.musicapiproject.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenID", nullable = false)
    private Integer id;

    @Column(name = "authToken", nullable = false)
    private String authToken;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "roleID", nullable = false)
    private Role roleID;

    @Column(name = "dateCreated", nullable = false)
    private LocalDate dateCreated;

    public Token(String authToken, String email, Role roleID, LocalDate dateCreated) {
        this.authToken = authToken;
        this.email = email;
        this.roleID = roleID;
        this.dateCreated = dateCreated;
    }

    public Token() {
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Role getRoleID() {
        return roleID;
    }

    public void setRoleID(Role roleID) {
        this.roleID = roleID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}