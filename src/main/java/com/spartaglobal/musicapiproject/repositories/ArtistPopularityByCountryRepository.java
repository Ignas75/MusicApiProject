package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.ArtistPopularityByCountry;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistPopularityByCountryRepository extends JpaRepository<ArtistPopularityByCountry, String> {
    List<ArtistPopularityByCountry> getTopByPopularity(Sort sort);
}