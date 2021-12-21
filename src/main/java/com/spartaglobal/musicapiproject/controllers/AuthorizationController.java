package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Employee;
import com.spartaglobal.musicapiproject.entities.Role;
import com.spartaglobal.musicapiproject.entities.Token;
import com.spartaglobal.musicapiproject.repositories.CustomerRepository;
import com.spartaglobal.musicapiproject.repositories.EmployeeRepository;
import com.spartaglobal.musicapiproject.repositories.TokenRepository;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import com.spartaglobal.musicapiproject.services.PopularityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AuthorizationController {

    private static final String validCharset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTYUVWXYZ0123456789";

    @Autowired
    private AuthorizationService as = new AuthorizationService();
    @Autowired
    private PopularityService popularityService = new PopularityService();
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
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        Set<String> allExistingTokenStrings = tokenRepository
                .findAll()
                .stream()
                .map(Token::getAuthToken)
                .collect(Collectors.toSet());
        boolean generatedUniqueToken = false;
        while (!generatedUniqueToken){
            for (int i = 0 ; i < 20 ; i++){
                sb.append(validCharset.charAt(rng.nextInt(0, validCharset.length())));
            }
            if (!allExistingTokenStrings.contains(sb.toString())){
                generatedUniqueToken = true;
            }
        }
        Role role;
        if (employeeRepository.existsByEmail(emailAddress)){
            Employee employee = employeeRepository.findByEmail(emailAddress);
            if (employee.getTitle().contains("General Manager") || employee.getTitle().contains("IT")){
                role = new Role(1);
            } else role = new Role(2);
        } else if (customerRepository.existsByEmail(emailAddress)){
            role = new Role(3);
        } else{
            return new ResponseEntity<>("{\"message\": \"email address not registered\"}", headers, HttpStatus.NOT_FOUND);
        }
        Token newToken = new Token(
                sb.toString(),
                emailAddress,
                role,
                LocalDate.now()
        );
        if (tokenRepository.existsByEmail(emailAddress)){
            Token token = tokenRepository.getByEmail(emailAddress);
            token.setAuthToken(sb.toString());
            tokenRepository.save(token);
        }else {
            tokenRepository.save(newToken);
        }
        return new ResponseEntity<>("{\n\"email\": " + "\"" + emailAddress + "\",\n" + "\"token\": " + "\"" + sb + "\"\n}", headers, HttpStatus.OK);
    }

    @DeleteMapping("/chinook/clear-token")
    public ResponseEntity<String> clearExistingAuthToken(@RequestParam String emailAddress){
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        if(tokenRepository.existsByEmail(emailAddress)){
            tokenRepository.delete(tokenRepository.getByEmail(emailAddress));
            return new ResponseEntity<>("{\"message\": \"Token cleared. You will need a new token to use the services again.\"}", headers, HttpStatus.PRECONDITION_FAILED);
        } else {
            return new ResponseEntity<>("{\"message\": \"email address not registered\"}", headers, HttpStatus.NOT_FOUND);
        }
    }

    // THESE ARE PROOF OF CONCEPT URLS FOR THE AUTH SYSTEM
    @GetMapping("chinook/customer-page")
    public ResponseEntity<String> testFunction(@RequestHeader("Authorization") String authToken){
        if(as.isAuthorizedForAction(authToken.split(" ")[1], "chinook/customer-page")) {
            return new ResponseEntity<>("Authorized", HttpStatus.OK);
        } else return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("chinook/employee-page")
    public ResponseEntity<String> testFunction2(@RequestHeader("Authorization") String authToken){
        if(as.isAuthorizedForAction(authToken.split(" ")[1], "chinook/employee-page")) {
            return new ResponseEntity<>("Authorized", HttpStatus.OK);
        } else return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("chinook/admin-page")
    public ResponseEntity<?> testFunction3(@RequestHeader("Authorization") String authToken){
        if(as.isAuthorizedForAction(authToken.split(" ")[1], "chinook/admin-page")) {
            return new ResponseEntity<>(popularityService.findMostPopularItems("Album", 10), HttpStatus.OK);
        } else return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
    }

}
