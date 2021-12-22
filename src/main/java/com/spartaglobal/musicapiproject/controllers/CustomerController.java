package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.entities.Customer;
import com.spartaglobal.musicapiproject.entities.Invoice;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.AlbumRepository;
import com.spartaglobal.musicapiproject.repositories.CustomerRepository;
import com.spartaglobal.musicapiproject.repositories.InvoiceRepository;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private AuthorizationService as;
    @Autowired
    private InvoiceController ic;


    @PostMapping("/chinook/customer/create")
    public ResponseEntity<Customer> createCustomer(@RequestHeader("Authorization") String authTokenHeader,
                                                   @RequestBody Customer newCustomer) {
        String token = authTokenHeader.split(" ")[1];
        if (as.isAuthorizedForAction(token, "chinook/customer/create")) {
            return new ResponseEntity("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        customerRepository.save(newCustomer);
        return new ResponseEntity("Customer Created", HttpStatus.OK);
    }

    @GetMapping("/chinook/customer")
    public ResponseEntity<Customer> readCustomer(@RequestParam Integer id) {
        Customer customer = customerRepository.getById(id);
        return new ResponseEntity(customer, HttpStatus.OK);
    }

    @PutMapping("/chinook/customer/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer newState, @RequestHeader("Authorization") String authTokenHeader) {
        String token = authTokenHeader.split(" ")[1];
        if (as.isAuthorizedForAction(token, "chinook/customer/create")) {
            return new ResponseEntity("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        Optional<Customer> oldState = customerRepository.findById(newState.getId());
        if (oldState.isEmpty()) return null;
        customerRepository.save(newState);
        return new ResponseEntity("Customer updated", HttpStatus.OK);
    }


    @DeleteMapping("/chinook/customer/delete")
    public ResponseEntity<Customer> deleteCustomer(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader) {
        String token = authTokenHeader.split(" ")[1];
        if (as.isAuthorizedForAction(token, "chinook/customer/create")) {
            return new ResponseEntity("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        customerRepository.delete(customerRepository.getById(id));
        return new ResponseEntity("Customer deleted", HttpStatus.OK);
    }

    @GetMapping("/chinook/customer/tracks")
    public List<Track> getCustomerTracks(@RequestParam Integer customerId) {
        Customer customer = customerRepository.getById(customerId);
        List<Invoice> customerInvoices = invoiceRepository.findAll().stream()
                .filter(s -> s.getCustomerId().equals(customer)).toList();
        List<Track> customerTracks = new ArrayList<>();
        for (int i = 0; i < customerInvoices.size(); i++) {
            customerTracks.addAll(ic.getTracksFromInvoice(customerInvoices.get(i)));
        }
        return customerTracks;
    }

    public List<Track> getUserPurchasedTracksFromAlbum(Integer customerId, Integer albumId) {
        Album album = albumRepository.getById(albumId);
        List<Track> customerTracks = getCustomerTracks(customerId);
        return customerTracks.stream().filter(s -> s.getAlbumId().equals(album)).toList();
    }
}







