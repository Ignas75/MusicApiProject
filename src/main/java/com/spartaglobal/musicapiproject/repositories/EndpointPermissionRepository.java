package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.EndpointPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndpointPermissionRepository extends JpaRepository<EndpointPermission, Integer> {
    EndpointPermission getByUrl(String endpoint);
}