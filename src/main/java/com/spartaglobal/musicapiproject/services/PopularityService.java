package com.spartaglobal.musicapiproject.services;

import com.spartaglobal.musicapiproject.entities.ContentType;
import com.spartaglobal.musicapiproject.entities.Popularity;
import com.spartaglobal.musicapiproject.repositories.PopularityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopularityService {

    @Autowired
    private PopularityRepository popularityRepository;

    public List<Popularity> findMostPopularItems(String contentType, int limit){
        switch (contentType){
            case "Track" -> {
                return popularityRepository.findByContentType(new ContentType(1), Sort.by(Sort.Direction.DESC, "NumberOfPurchases")).subList(0, limit);
            }
            case "Album" -> {
                return popularityRepository.findByContentType(new ContentType(2), Sort.by(Sort.Direction.DESC, "NumberOfPurchases")).subList(0, limit);
            }
            case "Genre" -> {
                return popularityRepository.findByContentType(new ContentType(4), Sort.by(Sort.Direction.DESC, "NumberOfPurchases")).subList(0, limit);
            }
            case "Playlist" -> {
                return popularityRepository.findByContentType(new ContentType(3), Sort.by(Sort.Direction.DESC, "NumberOfPurchases")).subList(0, limit);
            }
            case "Artist" -> {
                return popularityRepository.findByContentType(new ContentType(5), Sort.by(Sort.Direction.DESC, "NumberOfPurchases")).subList(0, limit);
            }
        }
        return null;
    }
}