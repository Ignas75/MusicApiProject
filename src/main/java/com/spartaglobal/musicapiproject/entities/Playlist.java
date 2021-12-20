package com.spartaglobal.musicapiproject.entities;

import javax.persistence.*;

@Entity
@Table(name = "playlist")
public class Playlist implements UserSearchable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlaylistId", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 120)
    private String name;

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