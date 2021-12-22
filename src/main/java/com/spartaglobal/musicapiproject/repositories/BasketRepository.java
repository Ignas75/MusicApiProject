package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
}