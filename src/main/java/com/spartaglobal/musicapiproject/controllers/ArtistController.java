package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.Artist;
import com.spartaglobal.musicapiproject.entities.Invoiceline;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.ContentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private InvoicelineRepository invoicelineRepository;
    @Autowired
    private PlaylisttrackRepository playlistTrackRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AuthorizationService as;


    @GetMapping(value = "/chinook/artist")
    public ResponseEntity<?> getArtist(@RequestParam Integer id, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            Optional<Artist> result = artistRepository.findById(id);
            return new ResponseEntity<>(result.orElse(null), HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @PostMapping("/chinook/artist/create")
    public ResponseEntity<String> createArtist(@RequestHeader("Authorization") String authTokenHeader, @RequestBody Artist newArtist, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];

            if (!as.isAuthorizedForAction(token, "/chinook/artist/create")) {

                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            artistRepository.save(newArtist);
            return new ResponseEntity<>("Artist Created", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @PutMapping(value = "/chinook/artist/update")
    public ResponseEntity<String> updateTrack(@RequestBody Artist newState, @RequestHeader("Authorization") String authTokenHeader, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];

            if (!as.isAuthorizedForAction(token, "/chinook/artist/create")) {

                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            Optional<Artist> oldState = artistRepository.findById(newState.getId());
            if (oldState.isEmpty()) return null;
            artistRepository.save(newState);
            return new ResponseEntity<>("Track updated", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Transactional
    @DeleteMapping(value = "/chinook/artist/delete")
    public ResponseEntity<String> deleteArtist(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];

            if (!as.isAuthorizedForAction(token, "/chinook/artist/create")) {

                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);

            }
            Optional<Artist> artist = artistRepository.findById(id);
            if (artist.isPresent()) {
                // Check artist has purchased songs
                boolean noPurchasedTracks = true;
                List<Album> artistAlbums = albumRepository.findByArtistId(artist.get());
                for (Album album : artistAlbums) {
                    List<Track> albumTracks = trackRepository.findAllByAlbumId(album);
                    for (Track track : albumTracks) {
                        List<Invoiceline> invoiceLines = invoicelineRepository.findAllByTrackId(track);
                        if (invoiceLines.size() > 0) {
                            noPurchasedTracks = false;
                            break;
                        }
                    }
                    if (!noPurchasedTracks) {
                        break;
                    }
                }
                if (noPurchasedTracks) {
                    for (Album album : artistAlbums) {
                        List<Track> albumTracks = trackRepository.findAllByAlbumId(album);
                        for (Track track : albumTracks) {
                            System.out.println(track.getName());
                            playlistTrackRepository.deleteByIdTrackId(track.getId());
                            trackRepository.delete(track);
                        }
                        albumRepository.delete(album);
                    }
                    artistRepository.delete(artist.get());
                    return new ResponseEntity<>("Artist deleted", HttpStatus.OK);
                }
                return new ResponseEntity<>("Cannot delete an artist whose songs have been purchased", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Artist does not exist", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
