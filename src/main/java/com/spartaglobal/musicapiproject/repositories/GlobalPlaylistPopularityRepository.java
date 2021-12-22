package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.GlobalPlaylistPopularity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GlobalPlaylistPopularityRepository extends JpaRepository<GlobalPlaylistPopularity, String> {
    List<GlobalPlaylistPopularity> findAll(Sort sort);
}