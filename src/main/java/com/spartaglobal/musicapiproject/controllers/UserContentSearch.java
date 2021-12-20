package com.spartaglobal.musicapiproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping (value = "/chinook/track")
    public Optional<Track> getTrack(@RequestParam Integer id)
    {
        Optional<Track> result = trackRepository.findById(id);
        return result;
    }

    @GetMapping (value = "/chinook/tracks")
    public List<Track> getActors()
    {
        return trackRepository.findAll();
    }

    @GetMapping (value = "/chinook/search")
    public Set<UserSearchable> getQuery(@RequestParam String name)
    {
        List<Track> tracks = trackRepository.findAll();
        List<Album> albums = albumRepository.findAll();
        List<Artist> artists = artistRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        List<Playlist> playlists = playlistRepository.findAll();
        Set<UserSearchable> searchSet = new HashSet<>();

        for (Track t : tracks) {
            if (t.userSearch().equalsIgnoreCase(name)) searchSet.add(t);
        }
        for (Album a : albums) {
            if (a.userSearch().equalsIgnoreCase(name)) searchSet.add(a);
        }
        for (Artist a : artists) {
            if (a.userSearch().equalsIgnoreCase(name)) searchSet.add(a);
        }
        for (Genre g : genres) {
            if (g.userSearch().equalsIgnoreCase(name)) searchSet.add(g);
        }
        for (Playlist p : playlists) {
            if (p.userSearch().equalsIgnoreCase(name)) searchSet.add(p);
        }
        return searchSet;
    }
}