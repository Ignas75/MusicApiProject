package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Basket;
import com.spartaglobal.musicapiproject.entities.BasketId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, BasketId> {
}