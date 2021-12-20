package com.spartaglobal.musicapiproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.entities.UserSearchable;
import com.spartaglobal.musicapiproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserContentSearch {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private PlaylisttrackRepository playlisttrackRepository;
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
    public List<? extends UserSearchable> getQuery()
    {

        return null;
    }


}

/*
@RestController

public class ActorController {
    @Autowired
    private ActorRepository repository;
    @GetMapping (value = "/sakila/actors")
    public List<Actor> getActors()
    {
        return repository.findAll();
    }

    @GetMapping(value = "/sakila/actor")
    public Optional<Actor> getActor(@RequestParam Integer id)
    {
        Optional<Actor> result = repository.findById(id);
        return result;
    }

    @PutMapping(value = "/sakila/actor/update")
    public Actor updateActor(@RequestBody Actor newState)
    {
        // Get original State  // Store the new state in DB
        Optional<Actor> oldState = repository.findById(newState.getId());
        if(oldState.isEmpty()) return null;
        repository.save(newState);
        return newState;
    }
    @DeleteMapping(value="/sakila/actor/delete")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteActor(@RequestParam Integer id) {
        repository.deleteById(id);
    }

    @PostMapping(value = "/sakila/actor/add")
    public Actor insertActor(@RequestBody Actor newActor)
    {
        repository.save(newActor);
        return newActor;
    }

    @GetMapping(value="/example")
    public ResponseEntity<String> getResponse()
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("content-type", "whatever").body("This is my return value.");
    }
}

 */

