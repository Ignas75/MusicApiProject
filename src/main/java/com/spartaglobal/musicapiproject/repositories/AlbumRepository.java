package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Genre, Integer> {
}