package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Invoice;
import com.spartaglobal.musicapiproject.entities.Invoiceline;
import com.spartaglobal.musicapiproject.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoicelineRepository extends JpaRepository<Invoiceline, Integer> {
    public void deleteByTrackId(Track track);

    List<Invoiceline> findAllByInvoiceId(Invoice invoice);

    void deleteAllByInvoiceId(Invoice invoice);

    List<Invoiceline> findAllByTrackId(Track track);
}