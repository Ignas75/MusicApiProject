package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.GenrePopularityByCountry;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenrePopularityByCountryRepository extends JpaRepository<GenrePopularityByCountry, String> {
    List<GenrePopularityByCountry> findAllByBillingCountry(String country, Sort sort);
}