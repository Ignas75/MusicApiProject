package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;
@RestController
public class BasketController {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private PlaylistRepository playlistRepo;
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DiscontinuedTrackRepository discontinuedTrackRepository;


    @PostMapping(value="/chinook/basket/add-track")
    public ResponseEntity<String> addTrackToBasket(@RequestParam Integer trackId, @RequestParam Integer customerId ) {
        Basket basket = new Basket();
        Optional<Customer> findCustomer = customerRepository.findById(customerId);
        Optional <Track> findTrack = trackRepository.findById(trackId);
        Optional <DiscontinuedTrack> findDiscontinuedTrack = discontinuedTrackRepository.findById(trackId);
        if (findCustomer.isPresent()){
            System.out.println("cust present");
            if (findTrack.isPresent()) {
                System.out.println("hdu");
                if (findDiscontinuedTrack.isEmpty()) {
                    System.out.println("no discon");
                    return new ResponseEntity<String>("Unsuccessful! In discounted Track so not available", HttpStatus.NOT_FOUND);
                } else {
                    BasketId basketid1 = new BasketId();
                    basketid1.setTrackId(trackId);
                    basketid1.setCustomerId(customerId);
                    basket.setId(basketid1);
                    basketRepository.save(basket);
                    return new ResponseEntity<String>("Successful! Added to basket", HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<String>("Not a track", HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<String>("Not a customer", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/chinook/basket/delete")
    public ResponseEntity<String> deleteTrackFromBasket(@RequestParam Integer trackId, @RequestParam Integer customerId) {
        Basket basket = new Basket();
        BasketId nBasket = new BasketId();
        nBasket.setTrackId(trackId);
        nBasket.setCustomerId(customerId);
        Optional<Basket> findBasket = basketRepository.findById(nBasket);
        if(findBasket.isPresent()){
            basket.setId(nBasket);
            basketRepository.delete(basket); //the whole row
            return new ResponseEntity<String>("Deleted",HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Customer not found with the given track ", HttpStatus.NOT_FOUND);
        }
    }

    
}
