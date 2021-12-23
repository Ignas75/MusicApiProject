package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
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
    private InvoicelineRepository invoicelineRepository;
    @Autowired
    private AuthorizationService as = new AuthorizationService();
    @Autowired
    private CustomerController cc = new CustomerController();
    @Autowired
    private InvoiceService is;


    @GetMapping(value = "/chinook/playlist")
    public Playlist getPlaylist(@RequestParam Integer id) {
        Optional<Playlist> result = playlistRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Transactional
    @DeleteMapping(value = "/chinook/playlist/delete")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity deletePlaylist(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader) {
        // Authorization
        String token = authTokenHeader.split(" ")[1];
        if (!as.isAuthorizedForAction(token, "/chinook/playlist/delete")) {
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
    public Playlisttrack addTrackToPlaylist(@Valid @RequestBody Playlisttrack playlisttrack) {
        return playlisttrackRepository.save(playlisttrack);
    }

    @PostMapping(value = "/chinook/playlist/create")
    public ResponseEntity addPlaylist(@RequestHeader("Authorization") String authTokenHeader, @RequestBody Playlist newPlaylist) {
        String token = authTokenHeader.split(" ")[1];
        if(!as.isAuthorizedForAction(token,"/chinook/playlist/create")){
            return new ResponseEntity("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        playlistRepository.save(newPlaylist);
        return new ResponseEntity(newPlaylist, HttpStatus.OK);
    }

    @PutMapping(value = "/chinook/playlist/update")
    public ResponseEntity updatePlaylist(@RequestBody Playlist newState, @RequestHeader("Authorization") String authTokenHeader){
        String token = authTokenHeader.split(" ")[1];
        if(!as.isAuthorizedForAction(token,"/chinook/track/update")){
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        Optional<Playlist> oldState = playlistRepository.findById(newState.getId());
        if (oldState.isEmpty()) return null;

        playlistRepository.save(newState);
        return new ResponseEntity(newState, HttpStatus.OK);
    }

    @PostMapping(value = "/chinook/playlist/buy")
    public ResponseEntity<String> buyPlaylist(@RequestParam Integer id, @RequestHeader("Authorization") String authToken, @RequestHeader("Accept") String dataFormat ) {
        String token = authToken.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        if (dataFormat.equals("application/json")){
            headers.add("content-type", "application/json");
        }
        headers.add("content-type", "application/xml");
        if (!as.isAuthorizedForAction(token, "/chinook/playlist/buy")) {
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
                .filter(s -> Objects.equals(s.getId().getPlaylistId(), id))
                .toList();

        List<Track> allTracks = new ArrayList<>();
        for (Playlisttrack t : allPlaylistTracks) {
            allTracks.add(trackRepository.getById(t.getId().getTrackId()));
        }
        allTracks.remove(cc.getCustomerTracks(customer.getId()));
        if(is.createInvoice(allTracks, customer)){
            return new ResponseEntity<>("Invoice(s) created", HttpStatus.OK);
        }
        return new ResponseEntity<>("Customer already owns all tracks in the playlist", HttpStatus.OK);
    }
}
