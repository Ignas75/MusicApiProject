package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Playlist;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.InvoicelineRepository;
import com.spartaglobal.musicapiproject.repositories.PlaylisttrackRepository;
import com.spartaglobal.musicapiproject.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;


@RestController
public class TrackController {
    @Autowired
    private TrackRepository trackRepo;
    @Autowired
    private InvoicelineRepository invoicelineRepo;
    @Autowired
    private PlaylisttrackRepository playlisttrackRepo;

    @GetMapping(value = "/chinook/track")
    public Track getTrack(@RequestParam Integer id) {
        Optional<Track> result = trackRepo.findById(id);
        if(result.isPresent()){
            return result.get();
        } else {
            return null;
        }
    }

    @Transactional
    @DeleteMapping(value = "/chinook/delete/track")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTrack(@RequestParam Integer id) {
        invoicelineRepo.deleteByTrackId(trackRepo.getById(id));
        playlisttrackRepo.deleteByIdTrackId(id);
        trackRepo.deleteById(id);
    }

    @PostMapping(value="/Chinook/new-track")
    public Track createTrack(@Valid @RequestBody Track track1){
        return trackRepo.save(track1);
    }

    @PatchMapping(value="/Chinook/update-track")
    public Track updateAlbum(@Valid @RequestBody Track track1){
        Optional<Track> res = trackRepo.findById(track1.getId());
        if(res.isPresent()){
            trackRepo.save(track1);
            return track1;
        } else {
            return null;
        }
    }
}
