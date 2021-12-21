package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Track> getUserPurchasedTracksFromAlbum(Integer customerId, Integer albumId){
        List<Track> purchasedTracksFromAlbum = new ArrayList<>();
        Optional<Album> findAlbum = albumRepository.findById(albumId);
        Optional<Customer> findCustomer = customerRepository.findById(customerId);
        if(findAlbum.isEmpty() || findCustomer.isEmpty()){
            return purchasedTracksFromAlbum;
        }
        List<Track> userTracks = getUserTracks(customerId);
        for(Track track: userTracks){
            if(track.getAlbumId().getId().equals(albumId)){
                purchasedTracksFromAlbum.add(track);
            }
        }
        return purchasedTracksFromAlbum;
    }

    public List<Track> getAlbumTracks(Integer albumId){
        return trackRepository.findAll().stream().filter(s -> s.getAlbumId().getId().equals(albumId)).toList();
    }


    // return album price
    // TODO: needs to check for discounts in a discount table and whether they still apply
    @GetMapping(value = "album/cost")
    public ResponseEntity<String> getAlbumCost(@RequestParam Integer albumId, @RequestParam Integer customerId){
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
    @PostMapping(value = "album/purchase")
    public ResponseEntity<String> purchaseAlbum(@RequestParam Integer albumId, @RequestParam Integer customerId,
                                                @RequestParam String billingAddress, @RequestParam String billingCity,
                                                @RequestParam String billingCountry, @RequestParam String postalCode){
        Optional<Customer> findCustomer = customerRepository.findById(customerId);
        // checking if the Ids are valid
        if(findCustomer.isEmpty()){
            return new ResponseEntity<>("No customer entry exists with that id", HttpStatus.NOT_FOUND);
        }
        Optional<Album> findAlbum = albumRepository.findById(albumId);
        if(findAlbum.isEmpty()){
            return new ResponseEntity<>("No album entry exists with that id", HttpStatus.NOT_FOUND);
        }
        Customer customer = findCustomer.get();

        List<Track> purchasedTracksFromAlbum = getUserPurchasedTracksFromAlbum(customerId, albumId);
        List<Track> albumTracks = getAlbumTracks(albumId);
        List<Track> tracksToPurchase = new ArrayList<>();
        BigDecimal totalCost = new BigDecimal(0);
        for(Track track: albumTracks){
            if(!purchasedTracksFromAlbum.contains(track)){
                tracksToPurchase.add(track);
                totalCost.add(track.getUnitPrice());
            }
        }
        if(!tracksToPurchase.isEmpty()){
            Invoice invoice = new Invoice();
            invoice.setBillingAddress(billingAddress);
            invoice.setBillingCity(billingCity);
            invoice.setBillingCountry(billingCountry);
            invoice.setBillingPostalCode(postalCode);
            invoice.setCustomerId(customer);
            invoice.setTotal(totalCost);
            invoiceRepository.save(invoice);
            for(Track track: tracksToPurchase){
                Invoiceline invoiceline = new Invoiceline();
                invoiceline.setInvoiceId(invoice);
                invoiceline.setQuantity(1);
                invoiceline.setUnitPrice(track.getUnitPrice());
                invoicelineRepository.save(invoiceline);
            }
        }
        return new ResponseEntity<>("Successfully registered purchase of " + tracksToPurchase.size() + " tracks",
                HttpStatus.OK);
    }

}
