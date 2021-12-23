package com.spartaglobal.musicapiproject.services;

import com.spartaglobal.musicapiproject.entities.Customer;
import com.spartaglobal.musicapiproject.entities.Invoice;
import com.spartaglobal.musicapiproject.entities.Invoiceline;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.InvoiceRepository;
import com.spartaglobal.musicapiproject.repositories.InvoicelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoicelineRepository invoiceLineRepository;

    public boolean createInvoice(List<Track> tracks, Customer customer){
        if(tracks.isEmpty()){
            return false;
        }
        Invoice newInvoice = new Invoice();
        newInvoice.setInvoiceDate(Instant.now());
        newInvoice.setBillingAddress(customer.getAddress());
        newInvoice.setBillingCountry(customer.getCountry());
        newInvoice.setBillingCity(customer.getCity());
        newInvoice.setBillingState(customer.getState());
        newInvoice.setBillingPostalCode(customer.getPostalCode());
        newInvoice.setCustomerId(customer);
        BigDecimal total = BigDecimal.valueOf(0);
        for (int i = 0; i < tracks.size(); i++) {
            total = total.add(tracks.get(i).getUnitPrice());
        }
        newInvoice.setTotal(total);
        invoiceRepository.save(newInvoice);
        List<Invoiceline> allInvoiceLineTracks = new ArrayList<>();
        for (int i = 0; i < tracks.size(); i++) {
            createInvoiceLine(newInvoice, tracks.get(i));
            allInvoiceLineTracks.add(createInvoiceLine(newInvoice, tracks.get(i)));
        }
        invoiceLineRepository.saveAllAndFlush(allInvoiceLineTracks);
        return true;
    }

    private Invoiceline createInvoiceLine(Invoice invoice, Track track){
        Invoiceline invoiceLine = new Invoiceline();
        invoiceLine.setInvoiceId(invoice);
        invoiceLine.setQuantity(1);
        invoiceLine.setTrackId(track);
        invoiceLine.setUnitPrice(track.getUnitPrice());
        return invoiceLine;
    }

    public List<Track> getTracksFromInvoice(Invoice invoice){
        List<Track> tracks = new java.util.ArrayList<>();
        List<Invoiceline> invoiceLines = invoiceLineRepository.findAll()
                .stream().filter(s->s.getInvoiceId().getId().equals(invoice.getId())).toList();
        for (Invoiceline invoiceLine : invoiceLines) {
            tracks.add(invoiceLine.getTrackId());
        }
        return tracks;
    }
}
