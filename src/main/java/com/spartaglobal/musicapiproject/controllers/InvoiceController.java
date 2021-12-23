package com.spartaglobal.musicapiproject.controllers;


import com.spartaglobal.musicapiproject.entities.*;
import com.spartaglobal.musicapiproject.repositories.*;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.ContentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoicelineRepository invoiceLineRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumDiscountRepository albumDiscountRepository;
    @Autowired
    private AlbumController ac;
    @Autowired
    private BulkPurchaseDiscountRepository bulkPurchaseDiscountRepository;
    @Autowired
    private AuthorizationService authorizationService;

    public boolean createInvoice(List<Track> tracks, Customer customer) {
        if (tracks.isEmpty()) {
            return false;
        }
        Invoice newInvoice = new Invoice();
        newInvoice.setInvoiceDate(Instant.now());
        newInvoice.setBillingAddress(customer.getAddress());
        newInvoice.setBillingCountry(customer.getCountry());
        newInvoice.setBillingCity(customer.getCity());
        newInvoice.setBillingState(customer.getState());
        newInvoice.setBillingPostalCode(customer.getPostalCode());
        newInvoice.setCustomerId(customer);
        BigDecimal total = BigDecimal.valueOf(0);
        total = getDiscountedPrice(tracks);
        newInvoice.setTotal(total);
        invoiceRepository.save(newInvoice);
        for (Track track : tracks) {
            createInvoiceLine(newInvoice, track);
        }
        return true;
    }

    private void createInvoiceLine(Invoice invoice, Track track) {
        Invoiceline invoiceLine = new Invoiceline();
        invoiceLine.setInvoiceId(invoice);
        invoiceLine.setQuantity(1);
        invoiceLine.setTrackId(track);
        invoiceLine.setUnitPrice(track.getUnitPrice());
        invoiceLineRepository.save(invoiceLine);
    }

    public List<Track> getTracksFromInvoice(Invoice invoice) {
        List<Track> tracks = new java.util.ArrayList<>();
        List<Invoiceline> invoiceLines = invoiceLineRepository.findAll()
                .stream().filter(s -> s.getInvoiceId().equals(invoice.getId())).toList();
        for (Invoiceline invoiceLine : invoiceLines) {
            tracks.add(invoiceLine.getTrackId());
        }
        return tracks;
    }


    public void viewInvoice() {
//TODO, Ignas to check with Neil
    }

    @DeleteMapping("/chinook/invoice/delete")

    public ResponseEntity<String> deleteInvoice(@RequestHeader("Authorization") String authTokenHeader, @RequestParam Integer id, @RequestHeader("Accept") String contentType) {
        if (ContentTypeService.getReturnContentType(contentType) != null) {
            String token = authTokenHeader.split(" ")[1];
            if (!authorizationService.isAuthorizedForAction(token, "chinook/invoice/delete")) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            invoiceRepository.delete(invoiceRepository.getById(id));
            return new ResponseEntity<>("Invoice Deleted", HttpStatus.OK);
        } else return new ResponseEntity<>("Unsupported Media Type Specified", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    private BigDecimal getDiscountedPrice(List<Track> tracks) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        ArrayList<Album> albumsInTracks = new ArrayList<Album>();
        List<AlbumDiscount> albumDiscount = albumDiscountRepository.findAll().stream().filter(s -> s.getLastValidDay().isBefore(LocalDate.now().plusDays(1))).toList();
        ArrayList<Album> currentAlbumOnDiscount = new ArrayList<Album>();

        for (AlbumDiscount discount : albumDiscount) {
            currentAlbumOnDiscount.add(discount.getAlbum());
        }

        for (Track track : tracks) {
            if (!albumsInTracks.contains(track.getAlbumId()) && currentAlbumOnDiscount.contains(track)) {
                albumsInTracks.add(track.getAlbumId());
            }
        }
        ArrayList<Track> bulkDiscountTracks = new ArrayList();
        for (Album albumsInTrack : albumsInTracks) {
            List<Track> tracksInAlbum = trackRepository.findByAlbumId(albumsInTrack);
            if (tracks.contains(tracksInAlbum)) {
                totalPrice = totalPrice.add(ac.getAlbumCostStandalone(albumsInTrack));
            } else {
                bulkDiscountTracks.addAll(tracksInAlbum);
            }
        }
        List<BulkPurchaseDiscount> listOfBulkDiscount = bulkPurchaseDiscountRepository.findAll().stream().filter(s -> s.getLastValidDay().isBefore(LocalDate.now().plusDays(1))).toList();
        Integer maxDiscount = 0;
        List<BulkPurchaseDiscount> applicableDiscounts = new ArrayList<>();
        for (BulkPurchaseDiscount bulkPurchaseDiscount : listOfBulkDiscount) {
            if (bulkPurchaseDiscount.getMinimumPurchased().compareTo(bulkDiscountTracks.size()) <= 0) {
                applicableDiscounts.add(bulkPurchaseDiscount);
            }
        }
        for (BulkPurchaseDiscount bulkPurchaseDiscount : listOfBulkDiscount) {

            if (bulkPurchaseDiscount.getDiscount() > maxDiscount) {
                maxDiscount = bulkPurchaseDiscount.getDiscount();
            }
        }
        BigDecimal bulkPrice = BigDecimal.ZERO;
        for (Track bulkDiscountTrack : bulkDiscountTracks) {
            bulkPrice = bulkPrice.add(bulkDiscountTrack.getUnitPrice().multiply(BigDecimal.valueOf(maxDiscount / 100)));
        }
        return totalPrice.add(bulkPrice);
    }
}
