package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.ContentType;
import com.spartaglobal.musicapiproject.entities.Popularity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopularityRepository extends JpaRepository<Popularity, Integer> {
    List<Popularity> findByContentType(ContentType contentType, Sort sort);
}