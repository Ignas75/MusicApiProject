package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}