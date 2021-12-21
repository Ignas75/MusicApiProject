package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.Invoiceline;
import com.spartaglobal.musicapiproject.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoicelineRepository extends JpaRepository<Invoiceline, Integer> {
    public void deleteByTrackId(Track track);
}