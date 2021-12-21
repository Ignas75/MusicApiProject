package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Employee;
import com.spartaglobal.musicapiproject.entities.EndpointPermission;
import com.spartaglobal.musicapiproject.entities.Role;
import com.spartaglobal.musicapiproject.entities.Token;
import com.spartaglobal.musicapiproject.repositories.CustomerRepository;
import com.spartaglobal.musicapiproject.repositories.EmployeeRepository;
import com.spartaglobal.musicapiproject.repositories.EndpointPermissionRepository;
import com.spartaglobal.musicapiproject.repositories.TokenRepository;
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
    private TokenRepository tokenRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EndpointPermissionRepository endpointpermissionRepository;

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
        if(isAuthorizedForAction(authToken.split(" ")[1], "chinook/customer-page")) {
            return new ResponseEntity<>("Authorized", HttpStatus.OK);
        } else return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("chinook/employee-page")
    public ResponseEntity<String> testFunction2(@RequestHeader("Authorization") String authToken){
        if(isAuthorizedForAction(authToken.split(" ")[1], "chinook/employee-page")) {
            return new ResponseEntity<>("Authorized", HttpStatus.OK);
        } else return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("chinook/admin-page")
    public ResponseEntity<String> testFunction3(@RequestHeader("Authorization") String authToken){
        if(isAuthorizedForAction(authToken.split(" ")[1], "chinook/admin-page")) {
            return new ResponseEntity<>("Authorized", HttpStatus.OK);
        } else return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
    }

    // Auth function, called with the token in a request and the endpoint hardcoded from inside the function tied to that endpoint
    /*
       This function should be called as the first part of your endpoint function if it requires authentication.
       The token should be supplied to your endpoint as part of the RequestHeader "Authorization" which should have value "Basic <token> (see above for examples)
       The endpoint URL should be hardcoded to match whichever mapping you are making the function for

       You are responsible for adding a line to the EndpointPermissions Table in the DB to make this work
       If you do not yet have this table, run this SQL then INSERT the necessary rows.
       CREATE TABLE EndpointPermissions(
            rowID INTEGER AUTO_INCREMENT PRIMARY KEY,
            url NVARCHAR(255) NOT NULL,
            isForCustomer BIT,
            isForStaff BIT,
            isForAdmins BIT
        );
     */
    public boolean isAuthorizedForAction(String authToken, String endpoint){
        if(tokenRepository.existsByAuthToken(authToken)){
            Token token = tokenRepository.getByAuthToken(authToken);
            EndpointPermission permissions = endpointpermissionRepository.getByUrl(endpoint);
            switch (token.getRoleID().getId()){
                case 1 -> {
                    if (permissions.getIsForAdmins()) return true;
                } case 2 -> {
                    if(permissions.getIsForStaff()) return true;
                } case 3 -> {
                    if (permissions.getIsForCustomer()) return true;
                }default -> {return false;}
            }
        }
        return false;
    }
}
