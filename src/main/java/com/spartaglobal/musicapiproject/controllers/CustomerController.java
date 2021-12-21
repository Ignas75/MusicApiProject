package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Customer;
import com.spartaglobal.musicapiproject.entities.Token;
import com.spartaglobal.musicapiproject.repositories.CustomerRepository;
import com.spartaglobal.musicapiproject.repositories.TokenRepository;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AuthorizationService aS = new AuthorizationService();

    public Customer getCustomerByEmail(String emailAddress){
        if (customerRepository.existsByEmail(emailAddress)){
            Customer customer = customerRepository.getCustomerByEmail(emailAddress);
            return customer;
         }
        return null;
    }

    //GET any customer details if they are admin or sales,
    @GetMapping(value = "chinook/sales/customer")
    public ResponseEntity<?> getCustomerByEmailAddress(@RequestParam String emailAddress, @RequestHeader("Authorization") String authToken) {
        String token[] = authToken.split(" ");
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        if (aS.isAuthorizedForAction(token[1], "chinook/sales/customer")) {
            Token token2 = tokenRepository.getByAuthToken(token[1]);
            if (!token2.getRoleID().equals(3)) {
                return ResponseEntity.ok(customerRepository.getCustomerByEmail(emailAddress));
            }
        }
        return new ResponseEntity<String>("{\"message\":\"You're not authorized!\"}", headers, HttpStatus.OK);
    }
}







