package com.spartaglobal.musicapiproject.controllers;

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


@RestController
public class TrackController {
    @Autowired
    private TrackRepository trackRepo;
    @Autowired
    private InvoicelineRepository invoicelineRepo;
    @Autowired
    private PlaylisttrackRepository playlisttrackRepo;

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
}
