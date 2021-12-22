package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.Artist;
import com.spartaglobal.musicapiproject.entities.Playlist;
import com.spartaglobal.musicapiproject.repositories.AlbumRepository;
import com.spartaglobal.musicapiproject.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumController albumController;

    @GetMapping(value = "/chinook/artist")
    public Artist getArtist(@RequestParam Integer id) {
        Optional<Artist> result = artistRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        } else {
            return null;
        }
    }

    @PatchMapping(value="/Chinook/update-artist")
    public Artist updateArtist(@Valid @RequestBody Artist id ){
        Optional<Artist> res = artistRepository.findById(id.getId());
        if(res.isPresent()){
            artistRepository.save(id);
            return id;
        } else {
            return null;
        }
    }

    @DeleteMapping(value="/chinook/delete/artist")
    public ResponseEntity<Integer> deleteArtist(@RequestParam Integer id) {
        Optional<Artist> artist = artistRepository.findById(id);
        if(artist.isPresent()){
            List<Album> artistAlbums = albumRepository.findByArtistId(artist.get());
            for (Album album : artistAlbums) {
                albumController.deleteAlbum(album.getId());
            }
            artistRepository.deleteById(id);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
