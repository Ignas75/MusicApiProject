package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.repositories.InvoicelineRepository;
import com.spartaglobal.musicapiproject.repositories.PlaylisttrackRepository;
import com.spartaglobal.musicapiproject.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

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
}
