package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Playlisttrack;
import com.spartaglobal.musicapiproject.repositories.PlaylistRepository;
import com.spartaglobal.musicapiproject.repositories.PlaylisttrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



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

    @PostMapping(value="/Chinook/new-playlist-track")
    public Playlisttrack createPlaylist(@Valid @RequestBody Playlisttrack playlisttrack){
        return playlistTrackRepo.save(playlisttrack);
    }
}
