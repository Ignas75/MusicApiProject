package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.GlobalArtistPopularity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GlobalArtistPopularityRepository extends JpaRepository<GlobalArtistPopularity, String> {
    List<GlobalArtistPopularity> findAll(Sort sort);
}