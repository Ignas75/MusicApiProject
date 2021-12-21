package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.GenrePopularityByCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenrePopularityByCountryRepository extends JpaRepository<GenrePopularityByCountry, String> {
}