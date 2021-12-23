package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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

    @PostMapping("chinook/customer/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        Customer existCustomer = customerRepository.getCustomerByEmail(newCustomer.getEmail());
        if (existCustomer != null) return new ResponseEntity("{\"message\":\"Customer Already Exist\"}",headers,HttpStatus.OK);
        customerRepository.save(newCustomer);
        String message = "{\"message\":\"Account Created\",\"customer\":" + newCustomer.getCustomer()+"}";
        return new ResponseEntity(message,headers,HttpStatus.OK);
    }

    @GetMapping("chinook/customer")
    public ResponseEntity<String> readCustomer(@RequestHeader("Authorization") String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        if (as.isAuthorizedForAction(authToken.split(" ")[1], "chinook/customer")){
            Token token = tokenRepository.getByAuthToken(authToken.split(" ")[1]);
            Customer customer = customerRepository.getCustomerByEmail(token.getEmail());
            String returnMessage = "{\"message\":\"Your Account Details\",\"customer\":" + customer.getCustomer() + "}";
        return new ResponseEntity<String>(returnMessage,headers,HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"message\":\"Not Authorized\"}",headers,HttpStatus.OK);
    }


    @PutMapping("chinook/customer/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer newState, @RequestHeader("Authorization") String authTokenHeader) {
        String token = authTokenHeader.split(" ")[1];
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
         if (!as.isAuthorizedForAction(token, "chinook/customer/update")) {
            return new ResponseEntity("{\"message\":\"Not Authorized\"}",headers, HttpStatus.UNAUTHORIZED);
        Token token1 = tokenRepository.getByAuthToken(token);
        Customer oldState = customerRepository.getCustomerByEmail(token1.getEmail());
        if (oldState == null) return new ResponseEntity("{\"message\":\"Not to change\"}",headers, HttpStatus.OK);
        if (newState.getAddress() != null) oldState.setAddress(newState.getAddress());
        if (newState.getState() != null) oldState.setState(newState.getState());
        if (newState.getCity() != null) oldState.setCity(newState.getCity());
        if (newState.getCountry() != null) oldState.setCountry(newState.getCountry());
        if (newState.getPostalCode() != null) oldState.setPostalCode(newState.getPostalCode());
        if (newState.getPhone() != null) oldState.setPhone(newState.getPhone());
        if (newState.getFax() != null) oldState.setFax(newState.getFax());
        if (newState.getSupportRepId() != null) oldState.setSupportRepId(newState.getSupportRepId());
        customerRepository.save(oldState);
        String returnMessage = "{\"message\":\"Account Updated\",\"customer\":" + oldState.getCustomer() + "}";
        return new ResponseEntity(returnMessage,headers, HttpStatus.OK);
    }


    @GetMapping("chinook/customer/tracks")
    public List<Track> getCustomerTracks( @RequestParam Integer customerId) {
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
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        String token = authTokenHeader.split(" ")[1];
        if (!as.isAuthorizedForAction(token, "chinook/customer/delete")) {
            return new ResponseEntity("{\"message\":\"Not Authorized\"}",headers, HttpStatus.UNAUTHORIZED);
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
        return new ResponseEntity("{\"message\":\"Customer deleted\"}",headers, HttpStatus.OK);
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







