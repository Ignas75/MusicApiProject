package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.DiscontinuedTrack;
import com.spartaglobal.musicapiproject.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscontinuedTrackRepository extends JpaRepository<DiscontinuedTrack, Integer> {
    DiscontinuedTrack findByTrackId(Track track);
}