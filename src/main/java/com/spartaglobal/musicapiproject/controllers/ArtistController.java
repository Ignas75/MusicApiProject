package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Artist;
import com.spartaglobal.musicapiproject.repositories.AlbumRepository;
import com.spartaglobal.musicapiproject.repositories.ArtistRepository;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumController albumController;
    @Autowired
    private AuthorizationService as;


    @GetMapping(value = "/chinook/artist")
    public Artist getArtist(@RequestParam Integer id) {
        Optional<Artist> result = artistRepository.findById(id);
        return result.orElse(null);
    }

    @PostMapping("/chinook/artist/create")
    public ResponseEntity<String> createArtist(@RequestHeader("Authorization") String authTokenHeader, @RequestBody Artist newArtist) {
        String token = authTokenHeader.split(" ")[1];
        if (!as.isAuthorizedForAction(token, "chinook/artist/create")) {
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        artistRepository.save(newArtist);
        return new ResponseEntity<>("Artist Created", HttpStatus.OK);
    }


    @PutMapping(value = "/chinook/artist/update")
    public ResponseEntity<String> updateTrack(@RequestBody Artist newState, @RequestHeader("Authorization") String authTokenHeader){
        String token = authTokenHeader.split(" ")[1];
        if(!as.isAuthorizedForAction(token,"chinook/artist/update")){
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        Optional<Artist> oldState = artistRepository.findById(newState.getId());
        if(oldState.isEmpty()) return null;
        artistRepository.save(newState);
        return new ResponseEntity("Artist updated", HttpStatus.OK);
    }

    @DeleteMapping(value = "/chinook/artist/delete")
    public ResponseEntity<String> deleteArtist(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader){
        String token = authTokenHeader.split(" ")[1];
        if(!as.isAuthorizedForAction(token,"chinook/artist/delete")){
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        artistRepository.delete(artistRepository.getById(id));
        return new ResponseEntity("Artist deleted", HttpStatus.OK);
    }
}
