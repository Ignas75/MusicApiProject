package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.ArrayList;
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
    BigDecimal totalPrice;

    @Autowired
    private AuthorizationService as = new AuthorizationService();

    //TODO Include the body to get the authentication token, to get the customer information//
    @RequestMapping(value = "/chinook/purchase-playlist/{playListId}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
//    @PostMapping(value = "/chinook/purchase-playlist")
    public ResponseEntity<String> getPlaylist(@PathVariable Integer playListId, @RequestHeader("Authorization") String authToken){
       if (!as.isAuthorizedForAction(authToken.split(" ")[1],"/chinook/purchase-playlist")){
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");

        /** Finds all the tracks based on the playlist id*/
        List<Playlisttrack> allPlaylistTracks = playlisttrackRepository.findAll().stream().filter(s -> s.getId().getPlaylistId() == playListId).toList();

        /**Storing the total price of the playlist 2240*/
        totalPrice = BigDecimal.valueOf(0);


        /** Predefine the Invoice tables invoice ID*/
        Invoice invoice = new Invoice();

        /**Gets the cusomter object*/
        Customer customer = customerRepository.getById(1);

        invoice.setInvoiceDate(Instant.now());
        invoice.setCustomerId(customer);
        invoice.setBillingAddress(customer.getAddress());
        invoice.setBillingState(customer.getState());
        invoice.setBillingCity(customer.getCity());
        invoice.setBillingCountry(customer.getCountry());
        invoice.setBillingPostalCode(customer.getPostalCode());
        invoice.setTotal(totalPrice);
        invoiceRepository.save(invoice);

       List<Invoiceline> batch = new ArrayList<>();

       for (Playlisttrack p : allPlaylistTracks){
               Invoiceline temp = new Invoiceline();
               temp.setInvoiceId(invoice);
               Track track = trackRepository.getById(p.getId().getTrackId());
                BigDecimal unitPrice = track.getUnitPrice();
                totalPrice = unitPrice.add(totalPrice);
               temp.setTrackId(track);
               temp.setUnitPrice(track.getUnitPrice());
               temp.setQuantity(1);
               batch.add(temp);
       }


       invoicelineRepository.saveAllAndFlush(batch);
        /**Update the total in the invoice*/
        Optional<Invoice> inv = invoiceRepository.findById(invoice.getId());
        inv.get().setTotal(totalPrice);
        invoiceRepository.save(invoice);
        return new ResponseEntity<String>("{\"message\":\"Playlist Purchase Complete\",\"Total Price\":\""+totalPrice+"\"}", headers, HttpStatus.OK);
    }
        return null;
   }






}
