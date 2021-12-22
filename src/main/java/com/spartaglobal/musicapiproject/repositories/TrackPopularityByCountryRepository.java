package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.TrackPopularityByCountry;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackPopularityByCountryRepository extends JpaRepository<TrackPopularityByCountry, String> {
    List<TrackPopularityByCountry> findAllByBillingCountry(String country, Sort sort);
}