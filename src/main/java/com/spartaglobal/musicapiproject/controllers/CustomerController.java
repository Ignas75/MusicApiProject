package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Customer;
import com.spartaglobal.musicapiproject.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;


    public Customer getCustomerByEmail(String emailAddress){
        if (customerRepository.existsByEmail(emailAddress)){
            Customer customer = customerRepository.getCustomerByEmail(emailAddress);
            return customer;
         }
        return null;
    }

}
