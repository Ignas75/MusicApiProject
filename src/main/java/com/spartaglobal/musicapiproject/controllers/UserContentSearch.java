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

    @GetMapping(value = "/chinook/search")
    public Set<UserSearchable> getQuery(@RequestParam String name) {
        List<Track> tracks = trackRepository.findAll();
        List<Album> albums = albumRepository.findAll();
        List<Artist> artists = artistRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        List<Playlist> playlists = playlistRepository.findAll();

        for (Track t : tracks) {
            wordChecker(t, name);
        }
        for (Album a : albums) {
            wordChecker(a, name);
        }
        for (Artist a : artists) {
            wordChecker(a, name);
        }
        for (Genre g : genres) {
            wordChecker(g, name);
        }
        for (Playlist p : playlists) {
            wordChecker(p, name);
        }
        return searchSet;
    }

    private void wordChecker(UserSearchable list, String name) {
        List<String> splitElement = Arrays.asList(list.userSearch().split(" "));
        for (String split : splitElement) {
            if (split.equalsIgnoreCase(name)) searchSet.add(list);
        }
    }
}