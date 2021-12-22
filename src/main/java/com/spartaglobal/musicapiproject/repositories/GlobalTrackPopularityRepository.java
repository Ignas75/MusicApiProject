package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.GlobalTrackPopularity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GlobalTrackPopularityRepository extends JpaRepository<GlobalTrackPopularity, String> {
    List<GlobalTrackPopularity> findAll(Sort sort);
}