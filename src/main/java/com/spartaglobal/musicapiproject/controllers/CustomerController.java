package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.AlbumRepository;
import com.spartaglobal.musicapiproject.repositories.CustomerRepository;
import com.spartaglobal.musicapiproject.repositories.InvoiceRepository;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.ContentTypeService;
import com.spartaglobal.musicapiproject.services.InvoiceService;
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
    private InvoiceService is;

    @PostMapping("/chinook/customer/create")
    public ResponseEntity<String> createCustomer(@RequestBody Customer newCustomer, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            if(customerRepository.existsByEmail(newCustomer.getEmail())) return new ResponseEntity<>("Customer already Exist", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            customerRepository.save(newCustomer);
            return new ResponseEntity<>("Customer Created", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @GetMapping("/chinook/customer")
    public ResponseEntity<?> readCustomer(@RequestParam Integer id, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            Customer customer = customerRepository.getById(id);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }


    @PutMapping("/chinook/customer/update")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer newState, @RequestHeader("Authorization") String authTokenHeader, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];
            if (as.isAuthorizedForAction(token, "chinook/customer/create")) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            Optional<Customer> oldState = customerRepository.findById(newState.getId());
            if (oldState.isEmpty()) return null;
            customerRepository.save(newState);
            return new ResponseEntity<>("Customer updated", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }


    @DeleteMapping("/chinook/customer/delete")
    public ResponseEntity<String> deleteCustomer(@RequestParam Integer id, @RequestHeader("Authorization") String authTokenHeader, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];
            if (as.isAuthorizedForAction(token, "chinook/customer/delete")) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            customerRepository.delete(customerRepository.getById(id));
            return new ResponseEntity<>("Customer deleted", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @GetMapping("/chinook/customer/tracks")
    public ResponseEntity<?> getCustomerTracks(@RequestParam Integer customerId, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            Customer customer = customerRepository.getById(customerId);
            List<Invoice> customerInvoices = invoiceRepository.findAll().stream()
                    .filter(s -> s.getCustomerId().equals(customer)).toList();
            List<Track> customerTracks = new ArrayList<>();
            for (int i = 0; i < customerInvoices.size(); i++) {
                customerTracks.addAll(is.getTracksFromInvoice(customerInvoices.get(i)));
            }
            return new ResponseEntity<>(customerTracks, HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    public List<Track> getUserPurchasedTracksFromAlbum(Integer customerId, Integer albumId) {
        Album album = albumRepository.getById(albumId);
        Customer customer = customerRepository.getById(customerId);
        List<Invoice> customerInvoices = invoiceRepository.findAll().stream()
                .filter(s -> s.getCustomerId().equals(customer)).toList();
        List<Track> customerTracks = new ArrayList<>();
        for (int i = 0; i < customerInvoices.size(); i++) {
            customerTracks.addAll(is.getTracksFromInvoice(customerInvoices.get(i)));
        }
        return customerTracks.stream().filter(s -> s.getAlbumId().equals(album)).toList();
    }


    private Archiveinvoice genArchiveInvoice(Invoice invoice) {
        Archiveinvoice archiveinvoice = new Archiveinvoice();
        archiveinvoice.setFirstName(invoice.getCustomerId().getFirstName());
        archiveinvoice.setLastName(invoice.getCustomerId().getLastName());
        archiveinvoice.setEmailAddress(invoice.getCustomerId().getEmail());
        archiveinvoice.setAddress(invoice.getBillingAddress() + " " + invoice.getBillingCity() + " " + invoice.getBillingState() + " " + invoice.getBillingCountry());
        archiveinvoice.setPostalCode(invoice.getBillingPostalCode());
        archiveinvoice.setInvoiceDate(invoice.getInvoiceDate());
        archiveinvoice.setTotal(invoice.getTotal());
        return archiveinvoice;
    }

    public List<Track> getAllCustomerTracks(Integer customerId) {
        Customer customer = customerRepository.getById(customerId);
        List<Invoice> customerInvoices = invoiceRepository.findAll().stream()
                .filter(s -> s.getCustomerId().equals(customer)).toList();
        List<Track> customerTracks = new ArrayList<>();
        for (int i = 0; i < customerInvoices.size(); i++) {
            customerTracks.addAll(is.getTracksFromInvoice(customerInvoices.get(i)));
        }
        return customerTracks;
    }
}







