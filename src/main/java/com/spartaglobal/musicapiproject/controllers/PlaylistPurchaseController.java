package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
public class PlaylistPurchaseController {


    @Autowired
    private PlaylisttrackRepository playlisttrackRepository;

    @Autowired
    private InvoicelineRepository invoicelineRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;



    @GetMapping(value = "/chinook/playlist")
    public List<Playlisttrack> getPlaylist(@RequestParam Integer playListId, @RequestParam Integer customerID){
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");


        /** Finds all the tracks based on the playlist id*/
        List<Playlisttrack> allPlaylistTracks = playlisttrackRepository.findAll().stream().filter(s -> s.getId().getPlaylistId() == playListId).toList();
        int numTrack = allPlaylistTracks.size();

        /**Used to get the last invoice number in the InvoiceLine Table*/
       // int num = (int) invoicelineRepository.count();



        /**Storing the total price of the playlist 2240*/
        BigDecimal totalPrice = BigDecimal.valueOf(0);


        /** Predefine the Invoice tables invoice ID*/
        Invoice invoice = new Invoice();

        /**Gets the cusomter object*/
        Customer customer = customerRepository.getById(customerID);

        invoice.setInvoiceDate(Instant.now());
        invoice.setCustomerId(customer);
        invoice.setBillingAddress(customer.getAddress());
        invoice.setBillingState(customer.getState());
        invoice.setBillingCity(customer.getCity());
        invoice.setBillingCountry(customer.getCountry());
        invoice.setBillingPostalCode(customer.getPostalCode());
        invoice.setTotal(totalPrice);
        invoiceRepository.save(invoice);



        /**Seting the object for Invoice line, each invoiceLine row in this playlist will belong to only one invoice*/


        //Change this to multi thread
        //from number of tracks
        for (int i =0; i < numTrack; i++){
            Invoiceline temp = new Invoiceline();
            temp.setInvoiceId(invoice); //InvoiceID
            Track track = trackRepository.getById(allPlaylistTracks.get(i).getId().getTrackId());
            BigDecimal unitPrice = track.getUnitPrice();
            totalPrice = unitPrice.add(totalPrice);
            temp.setTrackId(track);
            temp.setUnitPrice(track.getUnitPrice());
            temp.setQuantity(1);
            invoicelineRepository.save(temp);
        }

        /**Save the invoice at the end*/
        //save in the invoice


        /**Also need to be saved with dummy values*/
        Optional<Invoice> inv = invoiceRepository.findById(invoice.getId());
        inv.get().setTotal(totalPrice);
        invoiceRepository.save(invoice);
        return null;
    }





}
