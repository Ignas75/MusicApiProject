package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Playlisttrack;
import com.spartaglobal.musicapiproject.entities.PlaylisttrackId;
import com.spartaglobal.musicapiproject.repositories.PlaylisttrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class PlaylistPurchaseController {


    @Autowired
    private PlaylisttrackRepository playlisttrackRepository;


    @GetMapping(value = "/chinook/playlist/{id}")
    public List<Playlisttrack> getPlaylist(@RequestParam Integer id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        List<Playlisttrack> playlisttracks = playlisttrackRepository.findAll().stream().filter(s -> s.getId().getPlaylistId() == id).toList();
        return playlisttracks;

    }





}
