package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}