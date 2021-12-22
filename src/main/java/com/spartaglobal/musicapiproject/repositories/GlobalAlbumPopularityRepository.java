package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.GlobalAlbumPopularity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GlobalAlbumPopularityRepository extends JpaRepository<GlobalAlbumPopularity, String> {
    List<GlobalAlbumPopularity> findAll(Sort sort);
}