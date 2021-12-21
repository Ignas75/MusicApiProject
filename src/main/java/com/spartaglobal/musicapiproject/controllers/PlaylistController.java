package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Playlisttrack;
import com.spartaglobal.musicapiproject.repositories.PlaylisttrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class PlaylistController {
    @Autowired
    private PlaylisttrackRepository playlisttrackRepository;

    @PostMapping(value="/Chinook/new-playlist-track")
    public Playlisttrack createPlaylist(@Valid @RequestBody Playlisttrack playlisttrack){
        return playlisttrackRepository.save(playlisttrack);
    }
}
