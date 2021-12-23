package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Customer;
import com.spartaglobal.musicapiproject.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findAllByCustomerId(Customer customer);

    void deleteAllByCustomerId(Customer customer);
}