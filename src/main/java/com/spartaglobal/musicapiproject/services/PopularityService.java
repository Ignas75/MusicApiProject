package com.spartaglobal.musicapiproject.services;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PopularityService {

    @Autowired
    GlobalAlbumPopularityRepository globalAlbumPopularityRepository;
    @Autowired
    GlobalArtistPopularityRepository globalArtistPopularityRepository;
    @Autowired
    GlobalGenrePopularityRepository globalGenrePopularityRepository;
    @Autowired
    GlobalPlaylistPopularityRepository globalPlaylistPopularityRepository;
    @Autowired
    GlobalTrackPopularityRepository globalTrackPopularityRepository;
    @Autowired
    DiscontinuedTrackRepository discontinuedTrackRepository;

    public List<GlobalAlbumPopularity> findMostPopularAlbums(int limit, String sortDirection){
        if (sortDirection.equals("DESC")){
            return globalAlbumPopularityRepository.findAll(Sort.by(Sort.Direction.DESC, "Popularity")).subList(0, limit);
        } else if (sortDirection.equals("ASC")){
            return globalAlbumPopularityRepository.findAll(Sort.by(Sort.Direction.ASC, "Popularity")).subList(0, limit);
        } else return null;
    }

    public List<GlobalArtistPopularity> findMostPopularArtists(int limit, String sortDirection){
        if (sortDirection.equals("DESC")){
            return globalArtistPopularityRepository.findAll(Sort.by(Sort.Direction.DESC, "Popularity")).subList(0, limit);
        } else if (sortDirection.equals("ASC")){
            return globalArtistPopularityRepository.findAll(Sort.by(Sort.Direction.ASC, "Popularity")).subList(0, limit);
        } else return null;
    }

    public List<GlobalGenrePopularity> findMostPopularGenres(int limit, String sortDirection){
        if (sortDirection.equals("DESC")){
            return globalGenrePopularityRepository.findAll(Sort.by(Sort.Direction.DESC, "Popularity")).subList(0, limit);
        } else if (sortDirection.equals("ASC")){
            return globalGenrePopularityRepository.findAll(Sort.by(Sort.Direction.ASC, "Popularity")).subList(0, limit);
        } else return null;
    }

    public List<GlobalPlaylistPopularity> findMostPopularPlaylists(int limit, String sortDirection){
        if (sortDirection.equals("DESC")){
            return globalPlaylistPopularityRepository.findAll(Sort.by(Sort.Direction.DESC, "Popularity")).subList(0, limit);
        } else if (sortDirection.equals("ASC")){
            return globalPlaylistPopularityRepository.findAll(Sort.by(Sort.Direction.ASC, "Popularity")).subList(0, limit);
        } else return null;
    }

    public List<GlobalTrackPopularity> findMostPopularTracks(int limit, String sortDirection){
        if (sortDirection.equals("DESC")){
            List<GlobalTrackPopularity> allTracks = globalTrackPopularityRepository.findAll(Sort.by(Sort.Direction.DESC, "Popularity")).subList(0, limit);
            List<DiscontinuedTrack> discontinuedTracks = discontinuedTrackRepository.findAll();
            Set<String> discontinuedNames = discontinuedTracks.stream()
                    .map(DiscontinuedTrack::getTrackId)
                    .collect(Collectors.toSet())
                    .stream()
                    .map(Track::getName)
                    .collect(Collectors.toSet());
            return allTracks.stream()
                    .filter(e -> !discontinuedNames.contains(e.getName()))
                    .toList();
        } else if (sortDirection.equals("ASC")){
            return globalTrackPopularityRepository.findAll(Sort.by(Sort.Direction.ASC, "Popularity")).subList(0, limit);
        } else return null;
    }

    public <T> T findMostPopularItem(){
        Map<Long, T> highestPopularities = new HashMap<>();
        GlobalAlbumPopularity mostPopularAlbum = findMostPopularAlbums(1, "DESC").get(0);
        GlobalArtistPopularity mostPopularArtist = findMostPopularArtists(1, "DESC").get(0);
        GlobalPlaylistPopularity mostPopularPlaylist = findMostPopularPlaylists(1, "DESC").get(0);
        GlobalGenrePopularity mostPopularGenre = findMostPopularGenres(1, "DESC").get(0);
        GlobalTrackPopularity mostPopularTrack = findMostPopularTracks(1, "DESC").get(0);
        highestPopularities.put(mostPopularAlbum.getPopularity(), (T) mostPopularAlbum);
        highestPopularities.put(mostPopularArtist.getPopularity(), (T) mostPopularArtist);
        highestPopularities.put(mostPopularGenre.getPopularity(), (T) mostPopularGenre);
        highestPopularities.put(mostPopularPlaylist.getPopularity(), (T) mostPopularPlaylist);
        highestPopularities.put(mostPopularTrack.getPopularity(), (T) mostPopularTrack);
        return highestPopularities.get(Collections.max(highestPopularities.keySet()));
    }


}