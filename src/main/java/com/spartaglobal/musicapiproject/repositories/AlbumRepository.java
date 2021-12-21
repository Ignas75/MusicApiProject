package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
}