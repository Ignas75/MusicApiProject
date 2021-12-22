package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.AlbumPopularityByCountry;
import com.spartaglobal.musicapiproject.entities.ArtistPopularityByCountry;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumPopularityByCountryRepository extends JpaRepository<AlbumPopularityByCountry, String> {
    List<AlbumPopularityByCountry> findAllByBillingCountry(String country, Sort sort);
}