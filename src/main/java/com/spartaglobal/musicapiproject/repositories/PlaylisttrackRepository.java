package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Playlisttrack;
import com.spartaglobal.musicapiproject.entities.PlaylisttrackId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylisttrackRepository extends JpaRepository<Playlisttrack, PlaylisttrackId> {
    public void deleteByIdTrackId(Integer id);

    public List<Playlisttrack> findAllByIdPlaylistId(Integer id);
}