package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Playlisttrack;
import com.spartaglobal.musicapiproject.entities.PlaylisttrackId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylisttrackRepository extends JpaRepository<Playlisttrack, PlaylisttrackId> {
}