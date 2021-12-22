package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.Playlist;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.AlbumRepository;
import com.spartaglobal.musicapiproject.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepo;
    @Autowired
    private TrackRepository trackRepo;
    @Autowired
    private TrackController trackController;

    @RequestMapping(value = "/chinook/album/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Album getAlbum(@PathVariable Integer id) {
        Optional<Album> result = albumRepo.findById(id);
        if(result.isPresent()){
            return result.get();
        } else {
            return null;
        }
    }

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

    @PatchMapping(value="/Chinook/update-album")
    public Album updateAlbum(@Valid @RequestBody Album id ){
        Optional<Album> res = albumRepo.findById(id.getId());
        if(res.isPresent()){
            albumRepo.save(id);
            return id;
        } else {
            return null;
        }
    }


}


