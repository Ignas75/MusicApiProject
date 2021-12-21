package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.AlbumRepository;
import com.spartaglobal.musicapiproject.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;


import javax.transaction.Transactional;
import java.util.List;

@RestController
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepo;
    @Autowired
    private TrackRepository trackRepo;
    @Autowired
    private TrackController trackController;
    @Autowired
    private AlbumRepository albumRepo;

    @Transactional
    @DeleteMapping(value = "/chinook/delete/album")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAlbum(@RequestParam Integer id) {
        List<Track> albumTracks = trackRepo.findByAlbumId(albumRepo.getById(id));
        for (Track track : albumTracks) {
            trackController.deleteTrack(track.getId());
        }
        albumRepo.deleteById(id);
    }

    @PostMapping(value="/Chinook/new-album")
    public Album createAlbum(@Valid @RequestBody Album album1){
        return albumRepo.save(album1);
    }
}
