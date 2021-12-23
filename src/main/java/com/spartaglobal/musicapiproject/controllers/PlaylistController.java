package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.ContentTypeService;
import com.spartaglobal.musicapiproject.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class PlaylistController {

    @Autowired
    private PlaylisttrackRepository playlisttrackRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private PlaylisttrackRepository playlistTrackRepo;
    @Autowired
    private DiscontinuedTrackRepository discontinuedTrackRepository;

    private InvoicelineRepository invoicelineRepository;

    @Autowired
    private AuthorizationService as = new AuthorizationService();
    @Autowired
    private CustomerController cc = new CustomerController();
    @Autowired
    private InvoiceService is;

    @GetMapping(value = "/chinook/playlist")
    public ResponseEntity<?> getTrack(@RequestParam Integer id, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType)!= null) {
            Optional<Playlist> result = playlistRepository.findById(id);
            if (result.isPresent()) {
                return new ResponseEntity<>(result.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Playlist not Found", HttpStatus.NOT_FOUND);
            }
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Transactional
    @DeleteMapping(value = "chinook/playlist/delete")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity deletePlaylist(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader) {
        // Authorization
        String token = authTokenHeader.split(" ")[1];
        if (!as.isAuthorizedForAction(token, "chinook/playlist/delete")) {
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        // Check playlist exists
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if (playlist.isPresent()) {
            // Check if playlist contains purchased tracks
            boolean noPurchasedTracks = true;
            List<Playlisttrack> playlistTracks = playlisttrackRepository.findAllByIdPlaylistId(id);
            List<Track> tracks = new ArrayList<>();
            for (Playlisttrack playlisttrack : playlistTracks) {
                Track track = trackRepository.getById(playlisttrack.getId().getTrackId());
                tracks.add(track);
                List<Invoiceline> invoiceLines = invoicelineRepository.findAllByTrackId(track);
                if (invoiceLines.size() > 0) {
                    noPurchasedTracks = false;
                    break;
                }
            }
            if (noPurchasedTracks) {
                for (Track track : tracks) {
                    playlisttrackRepository.deleteByIdTrackId(track.getId());
                    trackRepository.delete(track);
                }
                playlistRepository.delete(playlist.get());
                return new ResponseEntity("Playlist deleted", HttpStatus.OK);
            }
            return new ResponseEntity("Cannot delete playlist containing purchased songs", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity("Playlist does not exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/chinook/playlist/add")
    public ResponseEntity<?> addTrackToPlaylist(@Valid @RequestBody Playlisttrack playlisttrack, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType)!= null) {
            return new ResponseEntity<>(playlistTrackRepo.save(playlisttrack), HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @PostMapping(value = "/chinook/playlist/remove")
    public ResponseEntity<?> removeTrackFromPlaylist(@Valid @RequestBody Playlisttrack playlisttrack, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType)!= null) {
            return new ResponseEntity<>(playlistTrackRepo.save(playlisttrack), HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @PostMapping(value = "/chinook/playlist/create")
    public ResponseEntity<?> addTrackToPlaylist(@Valid @RequestBody Playlist playlist, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType)!= null) {
            return new ResponseEntity<>(playlistRepository.save(playlist), HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @PatchMapping(value = "/chinook/playlist/update")
    public ResponseEntity<?> updateAlbum(@Valid @RequestBody Playlist playlist1, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            Optional<Playlist> res = playlistRepository.findById(playlist1.getId());
            if (res.isPresent()) {
                playlistRepository.save(playlist1);
                return new ResponseEntity<>(playlist1, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Playlist Not Found", HttpStatus.NOT_FOUND);
            }
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }


    @PostMapping(value = "chinook/playlist/buy")
    public ResponseEntity<String> buyPlaylist(@RequestParam Integer playListId, @RequestHeader("Authorization") String authToken) {
        String token = authToken.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        if (!as.isAuthorizedForAction(token, "chinook/playlist/buy")) {
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        Token user = tokenRepository.getByAuthToken(token);
        Customer customer = customerRepository.getCustomerByEmail(user.getEmail());
        if (customer == null) {
            return new ResponseEntity<>("{\"Customer not found\"}", HttpStatus.OK);
        }
        /* Finds all the tracks based on the playlist id*/
        List<Playlisttrack> allPlaylistTracks = playlisttrackRepository.findAll()
                .stream()
                .filter(s -> Objects.equals(s.getId().getPlaylistId(), playListId))
                .toList();
        List<Track> allTracks = new ArrayList<>();
        for (Playlisttrack t : allPlaylistTracks) {
            //check if track is discontinued
            Track track = trackRepository.getById(t.getId().getTrackId());
            Optional<DiscontinuedTrack> dtr = discontinuedTrackRepository.findById(track.getId());
            if (!(dtr.isEmpty()) && (dtr.get().getTrackId().getId() == track.getId())) {
                System.out.println("Discontinued!");
            } else {
                allTracks.add(track);
            }
        }
        allTracks.remove(cc.getAllCustomerTracks(customer.getId()));
        if(is.createInvoice(allTracks, customer)){
            return new ResponseEntity<>("Playlist Purchase Complete", HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"message\":\"Customer already owns all tracks in the playlist\"}", headers, HttpStatus.OK);
    }
}
