package com.spartaglobal.musicapiproject.controllers;
import com.spartaglobal.musicapiproject.entities.DiscontinuedTrack;
import com.spartaglobal.musicapiproject.repositories.DiscontinuedTrackRepository;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class DiscontinuedTrackController {

    @Autowired
    private DiscontinuedTrackRepository discontinuedTrackRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping(value = "/chinook/discontinuedtrack")
    public Optional<DiscontinuedTrack> getDiscontinuedTrack(@RequestParam Integer id) {
        Optional<DiscontinuedTrack> result = discontinuedTrackRepository.findById(id);
        return result;
    }

    @DeleteMapping(value = "/chinook/discontinuedtrack/delete")
    public ResponseEntity deleteDiscontinuedTrack(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader) {
        String token = authTokenHeader.split(" ")[1];
        if (authorizationService.isAuthorizedForAction(token, "chinook/discontinuedtrack/delete")) {
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        discontinuedTrackRepository.deleteById(id);
        return new ResponseEntity("Track removed from discontinued", HttpStatus.OK);
    }

    @PostMapping(value = "/chinook/discontinuedtrack/insert")
    public ResponseEntity insertDiscontinuedTrack(@RequestBody DiscontinuedTrack track, @RequestHeader("Authorization") String authTokenHeader) {
        String token = authTokenHeader.split(" ")[1];
        if (authorizationService.isAuthorizedForAction(token, "chinook/discontinuedtrack/insert")) {
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        discontinuedTrackRepository.save(track);
        return new ResponseEntity("Track inserted into discontinued", HttpStatus.OK);
    }

    @GetMapping(value = "/chinook/discontinuedtracks")
    public List<DiscontinuedTrack> getDiscontinuedTracks() {
        return discontinuedTrackRepository.findAll();
    }
}