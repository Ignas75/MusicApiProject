package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.PlaylistPopularityByCountry;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistPopularityByCountryRepository extends JpaRepository<PlaylistPopularityByCountry, String> {
    List<PlaylistPopularityByCountry> findAllByBillingCountry(String country, Sort sort);
}