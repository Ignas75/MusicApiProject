package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByEmailAddress(String emailAddress);
}