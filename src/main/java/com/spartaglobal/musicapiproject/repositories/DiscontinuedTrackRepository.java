package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.DiscontinuedTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscontinuedTrackRepository extends JpaRepository<DiscontinuedTrack, Integer> {
}