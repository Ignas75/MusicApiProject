package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class TrackController {
    @Autowired
    private TrackRepository trackRepository;


    @PostMapping(value="/Chinook/new-track")
    public Track createTrack(@Valid @RequestBody Track track1){
        return trackRepository.save(track1);
    }
}
