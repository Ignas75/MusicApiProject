package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}