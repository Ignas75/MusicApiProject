package com.spartaglobal.musicapiproject.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class AuthorizationController {

    @GetMapping("/chinook/create-token")
    public ResponseEntity<String> generateNewAuthToken(@RequestParam String emailAddress){
        final String validCharset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTYUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rng = new Random();
        for (int i = 0 ; i < 20 ; i++){
            sb.append(validCharset.charAt(rng.nextInt(0, validCharset.length())));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        return new ResponseEntity<>("{\n\"email\": " + "\"" + emailAddress + "\",\n" + "\"token\": " + "\"" + sb + "\"\n}", headers, HttpStatus.OK);
    }

}
