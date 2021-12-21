package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserContentSearch {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private TrackRepository trackRepository;

    Set<UserSearchable> searchSet = new HashSet<>();

    @GetMapping(value = "/chinook/track")
    public Optional<Track> getTrack(@RequestParam Integer id) {
        Optional<Track> result = trackRepository.findById(id);
        return result;
    }

    @GetMapping(value = "/chinook/tracks")
    public List<Track> getActors() {
        return trackRepository.findAll();
    }

    @GetMapping (value = "/chinook/search")
    public List<? extends UserSearchable> getQuery()
    {
        return null;
    }
}