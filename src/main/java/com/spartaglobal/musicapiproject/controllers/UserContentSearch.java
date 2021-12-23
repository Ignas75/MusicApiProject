package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
This class contains one function that acts like a search engine
you can enter a search string and it will search through
album names, artist names, genre names, playlist names and track names.

I don't recall seeing this in the client requirements.
If we want to keep it then we should refactor the code to use
streams and matchers for faster results.

*/

@RestController
public class UserContentSearch {
    Set<UserSearchable> searchSet = new HashSet<>();
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

    @GetMapping(value = "/chinook/search/track")
    public Optional<Track> getTrack(@RequestParam Integer id) {
        Optional<Track> result = trackRepository.findById(id);
        return result;
    }

    @GetMapping(value = "/chinook/search/tracks")
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
        String[] splitElement = list.userSearch().split(" ");
        for (String split : splitElement) {
            if (split.equalsIgnoreCase(name)) searchSet.add(list);
        }
    }
}