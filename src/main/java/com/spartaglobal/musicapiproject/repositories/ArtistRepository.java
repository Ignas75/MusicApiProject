package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
}