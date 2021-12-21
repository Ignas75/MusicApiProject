package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Customer;
import com.spartaglobal.musicapiproject.repositories.CustomerRepository;
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
    private AuthorizationService aS = new AuthorizationService();

    public Customer getCustomerByEmail(String emailAddress){
        if (customerRepository.existsByEmail(emailAddress)){
            Customer customer = customerRepository.getCustomerByEmail(emailAddress);
            return customer;
         }
        return null;
    }


    @GetMapping(value="chinook/sales/customer")
    public Customer getCustomerByEmailAddress(@RequestParam Integer customerID, @RequestHeader("Authorization") String authToken){
        String token[] = authToken.split(" ");
        if (aS.isAuthorizedForAction(token[1],"chinook/sales/customer")) {

        }
        return null;
    }
}






}
