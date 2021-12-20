package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PurchaseController {
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    InvoicelineRepository invoicelineRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TrackRepository trackRepository;

    public List<Track> getUserTracks(Integer customerId){
        List<Invoice> invoices = new ArrayList<>();
        for(Invoice invoice: invoiceRepository.findAll()){
            Customer customer = invoice.getCustomerId();
            if(customer.getId().equals(customerId)){
                invoices.add(invoice);
            }
        }

        List<Invoiceline> invoiceLines = new ArrayList<>();
        for(Invoiceline invoiceline: invoicelineRepository.findAll()){
            Integer invoiceLineInvoiceId = invoiceline.getInvoiceId().getId();
            for(Invoice invoice: invoices){
                if(invoice.getId().equals(invoiceLineInvoiceId)){
                    invoiceLines.add(invoiceline);
                }
            }
        }

        List<Track> userTracks = new ArrayList<>();
        for(Track track: trackRepository.findAll()){
            Integer trackId = track.getId();
            for(Invoiceline invoiceLine: invoiceLines){
                Integer invoiceTrackId = invoiceLine.getTrackId().getId();
                if(invoiceTrackId.equals(trackId)){
                    userTracks.add(track);
                }
            }
        }
        return userTracks;
    }


    // return album price
    // needs to check for discounts in a discount table and whether they still apply
    @GetMapping(value = "album/cost")
    public ResponseEntity<String> getAlbumCost(@RequestParam Integer albumId, @RequestParam Integer userId){
        Integer customerId = customerRepository.getById(userId).getId();
        List<Track> userTracks = getUserTracks(customerId);

        BigDecimal totalCost = new BigDecimal(0);
        for(Track track: userTracks){
            Integer trackAlbumId = track.getAlbumId().getId();
            if(trackAlbumId.equals(albumId)){
                totalCost.add(track.getUnitPrice());
            }
        }
        String cost = totalCost.toString();
        return new ResponseEntity<>(cost, HttpStatus.OK);
    }




    // purchase an album
    // need already purchased tracks from the user that belong to the current album
    // what if the same song is on another album?

}
