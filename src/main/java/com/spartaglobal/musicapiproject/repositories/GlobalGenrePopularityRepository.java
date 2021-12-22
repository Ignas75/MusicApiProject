package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.GlobalGenrePopularity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GlobalGenrePopularityRepository extends JpaRepository<GlobalGenrePopularity, String> {
    List<GlobalGenrePopularity> findAll(Sort sort);
}