package com.spartaglobal.musicapiproject.controllers;
import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.DiscontinuedTrack;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.DiscontinuedTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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

    @PostMapping(value = "/chinook/discontinuedtrack/insert")
    public DiscontinuedTrack insertDiscontinuedTrack(@RequestBody DiscontinuedTrack track) {
        return discontinuedTrackRepository.save(track);
    }

    @GetMapping(value = "/chinook/discontinuedtracks")
    public List<DiscontinuedTrack> getDiscontinuedTracks() {
        return discontinuedTrackRepository.findAll();
    }
}


