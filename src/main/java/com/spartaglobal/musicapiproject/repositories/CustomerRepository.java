package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String emailAddress);
    Customer getCustomerByEmail(String emailAddress);
}