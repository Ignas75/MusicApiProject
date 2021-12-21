package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    private TokenRepository tokenRepository;

    @Autowired
    private AuthorizationService as = new AuthorizationService();

    @Autowired
    private CustomerController cc = new CustomerController();

    //TODO Include the body to get the authentication token, to get the customer information//
    @PostMapping(value = "chinook/purchase-playlist")
    public ResponseEntity<String> getPlaylist(@RequestParam Integer playListId, @RequestHeader("Authorization") String authToken){
        String token = authToken.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
       if (as.isAuthorizedForAction(token,"chinook/purchase-playlist")){
           //Get the customer details
           Token user = tokenRepository.getByAuthToken(token);
           Customer customer = cc.getCustomerByEmail(user.getEmail());
           if (customer != null){

               /** Finds all the tracks based on the playlist id*/
               List<Playlisttrack> allPlaylistTracks = playlisttrackRepository.findAll()
                       .stream()
                       .filter(s -> s.getId().getPlaylistId() == playListId)
                       .toList();
               InvoiceController inv = new InvoiceController();
               List<Track> allTracks = new ArrayList<>();
               for (Playlisttrack t : allPlaylistTracks){
                   allTracks.add(trackRepository.getById(t.getId().getTrackId()));
               }
               inv.createInvoice(allTracks, customer);



/*
                *//**Storing the total price of the playlist 2240*//*
                totalPrice = BigDecimal.valueOf(0);


                *//** Predefine the Invoice tables invoice ID*//*
                Invoice invoice = new Invoice();

                *//**Gets the cusomter object*//*

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
               *//**Update the total in the invoice*//*
               Optional<Invoice> inv = invoiceRepository.findById(invoice.getId());
               inv.get().setTotal(totalPrice);
               invoiceRepository.save(invoice);*/
               return new ResponseEntity<>("{\"message\":\"Playlist Purchase Complete\",\"Total Price\":\"" + totalPrice + "\"}", headers, HttpStatus.OK);
    }
           return new ResponseEntity<>("{\"message\":\"Customer not found\"}", headers, HttpStatus.OK);
       }
       return new ResponseEntity<>("{\"message\":\"Access Denied\"}", headers, HttpStatus.OK);
    }

}
