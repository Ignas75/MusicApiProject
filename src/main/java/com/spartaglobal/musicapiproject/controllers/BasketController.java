package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.Basket;
import com.spartaglobal.musicapiproject.entities.Playlist;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.AlbumRepository;
import com.spartaglobal.musicapiproject.repositories.BasketRepository;
import com.spartaglobal.musicapiproject.repositories.PlaylistRepository;
import com.spartaglobal.musicapiproject.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasketController {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private PlaylistRepository playlistRepo;
    @Autowired
    private BasketRepository basketRepository;

    //List<Track> tracksBasket = new ArrayList<>();
    //List<Album> albumBasket = new ArrayList<>();
    ///List<Playlist> playlistBasket = new ArrayList<>();

    /*public BasketRepository addTracksBasket(Integer id){
        for(Track track: trackRepository.findAll())
            if(track.getId().equals(id))
                basketRepository.save(track);
        return basketRepository;
    }

     */

    @PostMapping(value ="/chinook/basket/add")
    public Basket addToDb(@RequestBody Basket b){
        if(b.getId().equals(trackRepository.findById(b.getId())))  //checking to see if the id is in track repo
            return basketRepository.save(b);
        else return null;
    }

    @PostMapping(value="//chinook/basket/add1")
    public Basket insertSomethingInBasket(@RequestBody Basket b) {
        return basketRepository.save(b);
    }

    @DeleteMapping(value="//chinook/basket/delete/{id}")
    public void deleteTrack(@RequestParam Integer id) {
        basketRepository.deleteById(id);
    }

 /*   public List<Album> addAlbumBasket(Integer id){
        for(Album a: albumRepository.findAll())
            if(a.getId().equals(id))
                albumBasket.add(a);
        return albumBasket;
    }

    public List<Playlist> addPlaylistBasket(Integer id){
        for(Playlist playlist: playlistRepo.findAll())
            if(playlist.getId().equals(id))
                playlistBasket.add(playlist);
        return playlistBasket;
    }

    public List<>
*/



}
