package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Integer> {
}