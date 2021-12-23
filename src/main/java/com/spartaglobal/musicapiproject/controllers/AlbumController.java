package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.Customer;
import com.spartaglobal.musicapiproject.entities.Invoiceline;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private InvoicelineRepository invoicelineRepository;
    @Autowired
    private PlaylisttrackRepository playlisttrackRepository;
    @Autowired
    private AuthorizationService as;
    @Autowired
    private CustomerController cc;
    @Autowired
    private InvoiceService is;

    @RequestMapping(value = "/chinook/album/{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Album getAlbum(@PathVariable Integer id) {
        Optional<Album> result = albumRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @GetMapping(value = "/chinook/album")
    public Album getTrack(@RequestParam Integer id) {
        Optional<Album> result = albumRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    @DeleteMapping(value = "/chinook/album/delete")
    public ResponseEntity deleteTrack(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader) {
        // Authorization
        String token = authTokenHeader.split(" ")[1];
        if (!as.isAuthorizedForAction(token, "/chinook/album/delete")) {

            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        // Check album exists
        Optional<Album> album = albumRepository.findById(id);
        if (album.isPresent()) {
            // Check if album contains any purchase songs
            boolean noPurchasedTracks = true;
            List<Track> albumTracks = trackRepository.findAllByAlbumId(album.get());
            for (Track track : albumTracks) {
                List<Invoiceline> invoiceLines = invoicelineRepository.findAllByTrackId(track);
                if (invoiceLines.size() > 0) {
                    noPurchasedTracks = false;
                    break;
                }
            }
            // Only delete if album doesn't contain any purchased
            if (noPurchasedTracks) {
                for(Track track : albumTracks) {
                    playlisttrackRepository.deleteByIdTrackId(track.getId());
                    trackRepository.delete(track);
                }
                albumRepository.delete(album.get());
                return new ResponseEntity("Album deleted", HttpStatus.OK);
            }
            return new ResponseEntity("Cannot delete album containing purchased songs", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity("Album does not exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/chinook/album/create")
    public ResponseEntity createAlbum(@RequestHeader("Authorization") String authTokenHeader, @RequestBody Album newAlbum) {
        String token = authTokenHeader.split(" ")[1];
        if (!as.isAuthorizedForAction(token, "chinook/album/create")) {
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        albumRepository.save(newAlbum);
        return new ResponseEntity<>("Album Created", HttpStatus.OK);
    }

    @PutMapping(value = "/chinook/album/update")
    public ResponseEntity updateAlbum(@RequestBody Album newState, @RequestHeader("Authorization") String authTokenHeader) {
        String token = authTokenHeader.split(" ")[1];
        if (!as.isAuthorizedForAction(token, "chinook/album/update")) {
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        Optional<Album> oldState = albumRepository.findById(newState.getId());
        if (oldState.isEmpty()) return null;
        albumRepository.save(newState);
        return new ResponseEntity("Album updated", HttpStatus.OK);
    }

    @GetMapping("/chinook/album/tracks")
    public List<Track> getAlbumTracks(@RequestParam Integer albumId) {
        return trackRepository.findAll().stream().filter(s -> s.getAlbumId().getId().equals(albumId)).toList();
    }

    @PostMapping("/chinook/album/buy")
    public ResponseEntity buyAlbum(@RequestParam Integer id, @RequestBody String authTokenHeader) {
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
        t.remove(cc.getUserPurchasedTracksFromAlbum(c.getId(),id));
        if (is.createInvoice(t, c)) {
            return new ResponseEntity<>("Invoice(s) created", HttpStatus.OK);
        }
        return new ResponseEntity<>("Customer owns all the tracks in album", HttpStatus.OK);
    }
}


