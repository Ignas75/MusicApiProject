package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.Artist;
import com.spartaglobal.musicapiproject.entities.Playlist;
import com.spartaglobal.musicapiproject.repositories.ArtistRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

public class ArtistController {
    private ArtistRepository artistRepository;

    @GetMapping(value = "/chinook/artist")
    public Artist getTrack(@RequestParam Integer id) {
        Optional<Artist> result = artistRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        } else {
            return null;
        }
    }

    @PatchMapping(value="/Chinook/update-artist")
    public Artist updateAlbum(@Valid @RequestBody Artist id ){
        Optional<Artist> res = artistRepository.findById(id.getId());
        if(res.isPresent()){
            artistRepository.save(id);
            return id;
        } else {
            return null;
        }
    }

}
