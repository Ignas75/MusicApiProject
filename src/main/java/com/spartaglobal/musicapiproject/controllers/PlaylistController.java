package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.Playlist;
import com.spartaglobal.musicapiproject.entities.Playlisttrack;
import com.spartaglobal.musicapiproject.repositories.PlaylistRepository;
import com.spartaglobal.musicapiproject.repositories.PlaylisttrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class PlaylistController {
    @Autowired
    private PlaylistRepository playlistRepo;
    @Autowired
    private PlaylisttrackRepository playlistTrackRepo;

    @Transactional
    @DeleteMapping(value = "/chinook/delete/playlist")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePlaylist(@RequestParam Integer id) {
        playlistTrackRepo.deleteByIdPlaylistId(id);
        playlistRepo.deleteById(id);
    }

    @PostMapping(value="/Chinook/new-playlist-track")
    public Playlisttrack createPlaylist(@Valid @RequestBody Playlisttrack playlisttrack){
        return playlistTrackRepo.save(playlisttrack);
    }

    @PatchMapping(value="/Chinook/update-playlist")
    public Playlist updateAlbum(@Valid @RequestBody Playlist playlist1){
        Optional<Playlist> res = playlistRepo.findById(playlist1.getId());
        if(res.isPresent()){
            playlistRepo.save(playlist1);
            return playlist1;
        } else {
            return null;
        }
    }
}
