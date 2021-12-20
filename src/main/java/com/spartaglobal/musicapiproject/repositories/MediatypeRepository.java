package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Mediatype;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediatypeRepository extends JpaRepository<Mediatype, Integer> {
}