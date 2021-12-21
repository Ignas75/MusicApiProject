package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
}