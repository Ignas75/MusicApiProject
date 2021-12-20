package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    boolean existsByEmail(String emailAddress);
    boolean existsByAuthToken(String authToken);
    Token getByEmail(String emailAddress);
    Token getByAuthToken(String authToken);
}