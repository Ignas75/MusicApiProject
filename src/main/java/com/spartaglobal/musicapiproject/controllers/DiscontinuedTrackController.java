package com.spartaglobal.musicapiproject.controllers;
import com.spartaglobal.musicapiproject.entities.DiscontinuedTrack;
import com.spartaglobal.musicapiproject.repositories.DiscontinuedTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class DiscontinuedTrackController {

    @Autowired
    private DiscontinuedTrackRepository discontinuedTrackRepository;

    @GetMapping(value = "/chinook/discontinuedtrack")
    public Optional<DiscontinuedTrack> getDiscontinuedTrack(@RequestParam Integer id) {
        Optional<DiscontinuedTrack> result = discontinuedTrackRepository.findById(id);
        return result;
    }

    @DeleteMapping(value = "/chinook/discontinuedtrack/delete")
    public void deleteDiscontinuedTrack(@RequestParam Integer id) {
        discontinuedTrackRepository.deleteById(id);
    }

    @PutMapping(value = "/chinook/discontinuedtrack/insert")
    public void insertDiscontinuedTrack(@RequestBody DiscontinuedTrack track) {
        discontinuedTrackRepository.save(track);
    }
}


