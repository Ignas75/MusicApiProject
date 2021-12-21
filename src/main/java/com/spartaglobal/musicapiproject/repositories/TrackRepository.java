package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Integer> {
    public void deleteByAlbumId(Album album);
    public List<Track> findByAlbumId(Album albumId);
}