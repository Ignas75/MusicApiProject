package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.DiscontinuedTrack;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.DiscontinuedTrackRepository;
import com.spartaglobal.musicapiproject.repositories.TrackRepository;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.ContentTypeService;
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

    @Autowired
    private TrackRepository trackRepository;

    // TODO: check if authentication would be advised to used here
    @GetMapping(value = "/chinook/discontinuedtrack")
    public ResponseEntity<?> getDiscontinuedTrack(@RequestParam Integer id, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            Optional<DiscontinuedTrack> result = discontinuedTrackRepository.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @DeleteMapping(value = "/chinook/discontinuedtrack/delete")
    public ResponseEntity<String> deleteDiscontinuedTrack(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];
            if (!authorizationService.isAuthorizedForAction(token, "/chinook/discontinuedtrack/delete")) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            discontinuedTrackRepository.deleteById(id);
            return new ResponseEntity<>("Track removed from discontinued", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @PostMapping(value = "/chinook/discontinuedtrack/insert")
    public ResponseEntity<String> insertDiscontinuedTrack(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];
            if (!authorizationService.isAuthorizedForAction(token, "/chinook/discontinuedtrack/insert")) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            DiscontinuedTrack dct = new DiscontinuedTrack();
            Optional<Track> findTrack = trackRepository.findById(id);
            if (findTrack.isEmpty()) {
                return new ResponseEntity<>("Track not found", HttpStatus.NOT_FOUND);
            }
            dct.setTrackId(findTrack.get());
            discontinuedTrackRepository.save(dct);
            return new ResponseEntity<>("Track inserted into discontinued", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @GetMapping(value = "/chinook/discontinuedtracks")
    public ResponseEntity<?> getDiscontinuedTracks(@RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            return new ResponseEntity<>(discontinuedTrackRepository.findAll(), HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}