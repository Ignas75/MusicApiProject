package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.PlaylistPopularityByCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistPopularityByCountryRepository extends JpaRepository<PlaylistPopularityByCountry, String> {
}