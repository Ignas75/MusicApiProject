package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.Customer;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.ContentTypeService;
import com.spartaglobal.musicapiproject.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AuthorizationService as;
    @Autowired
    private CustomerController cc;
    @Autowired
    private InvoiceService is;

    @GetMapping(value = "/chinook/album/{id}")
    public ResponseEntity<?> getAlbum(@PathVariable Integer id, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            Optional<Album> result = albumRepository.findById(id);
            return new ResponseEntity<>(result.orElse(null), HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @GetMapping(value = "/chinook/album")
    public ResponseEntity<?> getTrack(@RequestParam Integer id, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            Optional<Album> result = albumRepository.findById(id);
            return new ResponseEntity<>(result.orElse(null), HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Transactional
    @DeleteMapping(value = "/chinook/album/delete")
    public ResponseEntity<?> deleteTrack(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];
            if (as.isAuthorizedForAction(token, "chinook/album/delete")) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            trackRepository.delete(trackRepository.getById(id));
            return new ResponseEntity<>("Album deleted", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @PostMapping("/chinook/album/create")
    public ResponseEntity<?> createAlbum(@RequestHeader("Authorization") String authTokenHeader, @RequestBody Album newAlbum, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];
            if (as.isAuthorizedForAction(token, "chinook/album/create")) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            albumRepository.save(newAlbum);
            return new ResponseEntity<>("Album Created", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @PutMapping(value = "/chinook/album/update")
    public ResponseEntity<?> updateAlbum(@RequestBody Album newState, @RequestHeader("Authorization") String authTokenHeader, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];
            if (as.isAuthorizedForAction(token, "chinook/album/update")) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            Optional<Album> oldState = albumRepository.findById(newState.getId());
            if (oldState.isEmpty()) return null;
            albumRepository.save(newState);
            return new ResponseEntity<>("Album updated", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    }

    @GetMapping("/chinook/album/tracks")
    public ResponseEntity<?> getAlbumTracks(@RequestParam Integer albumId, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            return new ResponseEntity<>(trackRepository.findAll().stream().filter(s -> s.getAlbumId().getId().equals(albumId)).toList(), HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @PostMapping("/chinook/album/buy")
    public ResponseEntity<?> buyAlbum(@RequestParam Integer id, @RequestBody String authTokenHeader, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];
            if (as.isAuthorizedForAction(token, "chinook/track/buy")) {
                return new ResponseEntity<>("Not Customer", HttpStatus.UNAUTHORIZED);
            }
            String customerEmail;
            try {
                customerEmail = tokenRepository.findAll().stream().filter(s -> Objects.equals(s.getAuthToken(), token)).toList().get(0).getEmail();
            } catch (IndexOutOfBoundsException e) {
                return new ResponseEntity<>("Token Not Valid", HttpStatus.FORBIDDEN);
            }
            Album a = albumRepository.findById(id).stream().toList().get(0);
            if (a == null) {
                return new ResponseEntity<>("Track does not exist", HttpStatus.NO_CONTENT);
            }
            List<Track> t = trackRepository.findAllByAlbumId(albumRepository.getById(id));
            Customer c = customerRepository.findAll().stream().filter(s -> Objects.equals(s.getEmail(), customerEmail)).toList().get(0);
            t.remove(cc.getUserPurchasedTracksFromAlbum(c.getId(), id));
            if (is.createInvoice(t, c)) {
                return new ResponseEntity<>("Invoice(s) created", HttpStatus.OK);
            }
            return new ResponseEntity<>("Customer owns all the tracks in album", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}


