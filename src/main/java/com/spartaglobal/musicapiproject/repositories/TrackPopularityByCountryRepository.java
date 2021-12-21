package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.TrackPopularityByCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackPopularityByCountryRepository extends JpaRepository<TrackPopularityByCountry, String> {
}