package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Customer;
import com.spartaglobal.musicapiproject.entities.Invoice;
import com.spartaglobal.musicapiproject.entities.Invoiceline;
import com.spartaglobal.musicapiproject.entities.Track;
import com.spartaglobal.musicapiproject.repositories.InvoiceRepository;
import com.spartaglobal.musicapiproject.repositories.InvoicelineRepository;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoicelineRepository invoiceLineRepository;

    public void createInvoice(List<Track> tracks, Customer customer){
        Invoice newInvoice = new Invoice();
        newInvoice.setInvoiceDate(Instant.now());
        newInvoice.setBillingAddress(customer.getAddress());
        newInvoice.setBillingCity(customer.getCity());
        newInvoice.setBillingPostalCode(customer.getPostalCode());
        newInvoice.setCustomerId(customer);
        BigDecimal total = null;
        for (int i = 0; i < tracks.size(); i++) {
            total.add(tracks.get(i).getUnitPrice());
            createInvoiceLine(newInvoice, tracks.get(i));
        }
    }
    private void createInvoiceLine(Invoice invoice, Track track){
        Invoiceline invoiceLine = new Invoiceline();
        invoiceLine.setInvoiceId(invoice);
        invoiceLine.setQuantity(1);
        invoiceLine.setTrackId(track);
        invoiceLine.setUnitPrice(track.getUnitPrice());
        invoiceLineRepository.save(invoiceLine);
    }

    public void viewInvoice(){
//TODO, Ignas to check with Neil
    }
    @DeleteMapping("chinook/invoice/delete")
    public ResponseEntity deleteInvoice(@RequestHeader("Authorization") String authTokenHeader,@RequestParam Integer id){
        String token = authTokenHeader.split(" ")[1];
        AuthorizationService as = new AuthorizationService();
        if (!as.isAuthorizedForAction(token,"chinook/invoice/delete")){
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        invoiceRepository.delete(invoiceRepository.getById(id));
        return new ResponseEntity<>("Invoice Deleted", HttpStatus.OK);
    }

}
