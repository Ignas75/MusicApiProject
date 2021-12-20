package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Role;
import com.spartaglobal.musicapiproject.entities.Token;
import com.spartaglobal.musicapiproject.repositories.CustomerRepository;
import com.spartaglobal.musicapiproject.repositories.EmployeeRepository;
import com.spartaglobal.musicapiproject.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

@RestController
public class AuthorizationController {

    private static final String validCharset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTYUVWXYZ0123456789";

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/chinook/create-token")
    public ResponseEntity<String> generateNewAuthToken(@RequestParam String emailAddress){
        StringBuilder sb = new StringBuilder();
        Random rng = new Random();
        for (int i = 0 ; i < 20 ; i++){
            sb.append(validCharset.charAt(rng.nextInt(0, validCharset.length())));
        }
        Role role = null;
        if (employeeRepository.existsByEmailAddress(emailAddress)){
            
        } else if (customerRepository.existsByEmailAddress(emailAddress)){
            role = new Role(3);
        }
        Token newToken = new Token(
                sb.toString(),
                emailAddress,
                role,
                LocalDate.now()
        );
        tokenRepository.save(newToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        return new ResponseEntity<>("{\n\"email\": " + "\"" + emailAddress + "\",\n" + "\"token\": " + "\"" + sb + "\"\n}", headers, HttpStatus.OK);
    }

}
