package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.AlbumDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumDiscountRepository extends JpaRepository<AlbumDiscount, Integer> {
}