package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private PlaylistRepository playlistRepo;
    @Autowired
    private PlaylisttrackRepository playlistTrackRepo;
    @Autowired
    private DiscontinuedTrackRepository discontinuedTrackRepository;

    @Autowired
    private AuthorizationService as = new AuthorizationService();

    @Autowired
    private CustomerController cc = new CustomerController();

    @Autowired
    private InvoiceService is;




    @GetMapping(value = "/chinook/playlist")
    public Playlist getTrack(@RequestParam Integer id) {
        Optional<Playlist> result = playlistRepo.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Transactional
    @DeleteMapping(value = "/chinook/playlist/delete")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePlaylist(@RequestParam Integer id) {
        playlistTrackRepo.deleteByIdPlaylistId(id);
        playlistRepo.deleteById(id);
    }

    @PostMapping(value = "/chinook/playlist/add")
    public Playlisttrack addTrackToPlaylist(@Valid @RequestBody Playlisttrack playlisttrack) {
        return playlistTrackRepo.save(playlisttrack);
    }

    @PostMapping(value = "/chinook/playlist/remove")
    public Playlisttrack removeTrackFromPlaylist(@Valid @RequestBody Playlisttrack playlisttrack) {
        return playlistTrackRepo.save(playlisttrack);
    }

    @PostMapping(value = "/chinook/playlist/create")
    public Playlist addTrackToPlaylist(@Valid @RequestBody Playlist playlist) {
        return playlistRepo.save(playlist);
    }

    @PatchMapping(value = "/chinook/playlist/update")
    public Playlist updateAlbum(@Valid @RequestBody Playlist playlist1) {
        Optional<Playlist> res = playlistRepo.findById(playlist1.getId());
        if (res.isPresent()) {
            playlistRepo.save(playlist1);
            return playlist1;
        } else {
            return null;
        }
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
        allTracks.remove(cc.getCustomerTracks(customer.getId()));
        if (is.createInvoice(allTracks, customer)) {
            return new ResponseEntity<>("{\"message\":\"Playlist Purchase Complete\"}", headers, HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"message\":\"Customer already owns all tracks in the playlist\"}", headers, HttpStatus.OK);
    }
}
