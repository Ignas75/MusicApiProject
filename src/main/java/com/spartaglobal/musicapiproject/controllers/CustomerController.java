package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
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
    private InvoicelineRepository invoicelineRepository;
    @Autowired
    private ArchiveinvoiceRepository archiveinvoiceRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private AuthorizationService as;
    @Autowired
    private InvoiceService is;


    @PostMapping("/chinook/customer/create")
    public ResponseEntity<Customer> createCustomer(@RequestHeader("Authorization") String authTokenHeader,
                                                   @RequestBody Customer newCustomer) {
        String token = authTokenHeader.split(" ")[1];
        if (!as.isAuthorizedForAction(token, "chinook/customer/create")) {
            return new ResponseEntity("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        customerRepository.save(newCustomer);
        return new ResponseEntity("Customer Created", HttpStatus.OK);
    }

    // should require authentication to prevent accessing other people's data via id tests?
    // TODO: deal with customer not found/doesn't exist in response entity
    @GetMapping("/chinook/customer")
    public ResponseEntity<Customer> readCustomer(@RequestParam Integer id) {
        Customer customer = customerRepository.getById(id);
        return new ResponseEntity(customer, HttpStatus.OK);
    }


    @PutMapping("/chinook/customer/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer newState, @RequestHeader("Authorization") String authTokenHeader) {
        String token = authTokenHeader.split(" ")[1];
        if (!as.isAuthorizedForAction(token, "chinook/customer/update")) {
            return new ResponseEntity("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        Optional<Customer> oldState = customerRepository.findById(newState.getId());
        if (oldState.isEmpty()) return null;
        customerRepository.save(newState);
        return new ResponseEntity("Customer updated", HttpStatus.OK);
    }

    // TODO: check if needs to have authentication to prevent customers accessing each other's tracks
    @GetMapping("/chinook/customer/tracks")
    public List<Track> getCustomerTracks(@RequestParam Integer customerId) {
        Customer customer = customerRepository.getById(customerId);
        List<Invoice> customerInvoices = invoiceRepository.findAll().stream()
                .filter(s -> s.getCustomerId().equals(customer)).toList();
        List<Track> customerTracks = new ArrayList<>();
        for (int i = 0; i < customerInvoices.size(); i++) {
            customerTracks.addAll(is.getTracksFromInvoice(customerInvoices.get(i)));
        }
        return customerTracks;
    }


    // TODO: move to service since it is used elsewhere in AlbumController
    public List<Track> getUserPurchasedTracksFromAlbum(Integer customerId, Integer albumId) {
        Album album = albumRepository.getById(albumId);
        List<Track> customerTracks = getCustomerTracks(customerId);
        return customerTracks.stream().filter(s -> s.getAlbumId().equals(album)).toList();
    }


    @DeleteMapping("chinook/customer/delete")
    public ResponseEntity<Customer> deleteCustomer(@RequestHeader("Authorization") String authTokenHeader) {
        String token = authTokenHeader.split(" ")[1];
        if (!as.isAuthorizedForAction(token, "chinook/customer/delete")) {
            return new ResponseEntity("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        Token tk = tokenRepository.getByAuthToken(token);
        Customer customer  = customerRepository.getCustomerByEmail(tk.getEmail());

        //find all the invoices for that customer
        List<Invoice> invoices = invoiceRepository.findAllByCustomerId(customer);

        for (Invoice i : invoices){
            archiveinvoiceRepository.save(genArchiveInvoice(i));
            List<Invoiceline> invoicelines = invoicelineRepository.findAllByInvoiceId(i);
            invoicelineRepository.deleteAllInBatch(invoicelines);
        }
        invoiceRepository.deleteAllInBatch(invoices);
        customerRepository.delete(customer);
        return new ResponseEntity("Customer deleted", HttpStatus.OK);
    }


    private Archiveinvoice genArchiveInvoice(Invoice invoice){
        Archiveinvoice archiveinvoice = new Archiveinvoice();
        archiveinvoice.setFirstName(invoice.getCustomerId().getFirstName());
        archiveinvoice.setLastName(invoice.getCustomerId().getLastName());
        archiveinvoice.setEmailAddress(invoice.getCustomerId().getEmail());
        archiveinvoice.setAddress(invoice.getBillingAddress() +" "+  invoice.getBillingCity() +" "+  invoice.getBillingState() +" "+ invoice.getBillingCountry());
        archiveinvoice.setPostalCode(invoice.getBillingPostalCode());
        archiveinvoice.setInvoiceDate(invoice.getInvoiceDate());
        archiveinvoice.setTotal(invoice.getTotal());
        return archiveinvoice;
    }

}







