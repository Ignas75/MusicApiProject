package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Playlisttrack;
import com.spartaglobal.musicapiproject.repositories.PlaylistRepository;
import com.spartaglobal.musicapiproject.repositories.PlaylisttrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class PlaylistController {
    @Autowired
    private PlaylistRepository playlistRepo;
    @Autowired
    private PlaylisttrackRepository playlistTrackRepo;


    @Transactional
    @DeleteMapping(value = "/chinook/delete/playlist")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePlaylist(@RequestParam Integer id) {
        playlistTrackRepo.deleteByIdPlaylistId(id);
        playlistRepo.deleteById(id);
    }
}
