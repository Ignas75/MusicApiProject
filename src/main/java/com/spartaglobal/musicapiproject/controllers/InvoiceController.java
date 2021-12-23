package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.repositories.InvoiceRepository;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.ContentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController {

    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    private InvoiceRepository invoiceRepository;

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

}
