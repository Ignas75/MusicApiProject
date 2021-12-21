package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;

    @PostMapping(value="/Chinook/new-album")
    public Album createAlbum(@Valid @RequestBody Album album1){
        return albumRepository.save(album1);
    }
}
