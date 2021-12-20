package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Endpointpermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndpointpermissionRepository extends JpaRepository<Endpointpermission, Integer> {
    Endpointpermission getByUrl(String endpoint);
}