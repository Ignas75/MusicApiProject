package com.spartaglobal.musicapiproject.controllers;

import com.spartaglobal.musicapiproject.entities.Customer;
import com.spartaglobal.musicapiproject.entities.Token;
import com.spartaglobal.musicapiproject.repositories.CustomerRepository;
import com.spartaglobal.musicapiproject.repositories.TokenRepository;
import com.spartaglobal.musicapiproject.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


/***
 * Please add following lines of sql query into the database after the end
 */






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


    //get the customer detail based on the auth token
    @GetMapping(value = "chinook/customer")
    public ResponseEntity<?> requestCustomerDetails(@RequestHeader("Authorization") String authToken){
        String token[] = authToken.split(" ");
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        if (aS.isAuthorizedForAction(token[1], "chinook/customer")){
            Token userToken = tokenRepository.getByAuthToken(token[1]);
            Customer customer = customerRepository.getCustomerByEmail(userToken.getEmail());
            if (customer != null)
                return ResponseEntity.ok(customer);
        }
        return new ResponseEntity<>("{\"message\":\"Invalid Request\"}",headers, HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping(value = "chinook/sales/delete/customer")
    public ResponseEntity<?> deleteCustomerByCustomerID(@RequestParam Integer customerID, @RequestHeader("Authorization") String authToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        String token[] = authToken.split("chinook/sales/delete/customer");
        if (aS.isAuthorizedForAction(token[1], "")) {
            return new ResponseEntity<>("{\"message\":\"Invalid Request\"}", headers, HttpStatus.BAD_REQUEST);
        }
        return null;
    }






    @PatchMapping(value="chinook/sales/update")
    public ResponseEntity<?> updateCustomerByEmailAddress( @RequestParam Customer customer1, @RequestHeader("Authorization") String authToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type","application/json");
        String token[] = authToken.split(" ");
        if(aS.isAuthorizedForAction(token[1],"chinook/sales/update")){
            Token userToken = tokenRepository.getByAuthToken(token[1]);
            Customer customer = customerRepository.getCustomerByEmail(userToken.getEmail());
            if(customer != null)
                return ResponseEntity.ok(customer);
        }
        Optional<Customer> customer = customerRepository.findById(customer1.getId());
        if(customer.isEmpty()) {
            return new ResponseEntity<String>("{\"message\":\"Customer doesn't exist" + customer1.getId() + "\"}", headers, HttpStatus.OK);
        }
        customer.get().setFirstName(customer1.getFirstName());
        customer.get().setLastName(customer1.getLastName());
        customer.get().setCompany(customer1.getCompany());
        customer.get().setAddress(customer1.getAddress());
        customer.get().setCity(customer1.getCity());
        customer.get().setState(customer1.getState());
        customer.get().setCountry(customer1.getCountry());
        customer.get().setPostalCode(customer1.getPostalCode());
        customer.get().setPhone(customer1.getPhone());
        customer.get().setFax(customer1.getFax());
        customer.get().setEmail(customer1.getEmail());

        String message = "{\"messsage\" : \"Customer Update\", \"customer\":";
        String vals = " {\"id\":\"" + customer1.getId() + "\",\"firstName\":\"" + customer1.getFirstName() +
                         "\",\"lastName\":\"" + customer1.getLastName() +"\",\"company\":\"" + customer1.getCompany() +
                "\",\"Address\":\"" + customer1.getAddress() +"\",\"city\":\"" + customer1.getCity() +
                "\",\"State\":\"" + customer1.getState() + "\",\"country\":\"" + customer1.getCountry()+
                "\",\"postal code\":\"" + customer1.getPostalCode() + "\",\"phone\":\"" + customer1.getPhone()+
                "\",\"fax\":\"" + customer1.getFax() + "\",\"email\":\"" + customer1.getEmail() + "\"}";
        String bodymessage = message + vals + "}";
        return new ResponseEntity<String>(bodymessage, headers, HttpStatus.OK);
    }

    @PutMapping(value="/chinook/sales/new-customer")
    public ResponseEntity<Customer> newCustomerHere(@RequestParam Customer customer1, @RequestHeader("Authorization") String authToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type","application/json");
        String token[] = authToken.split(" ");
        if(aS.isAuthorizedForAction(token[1],"chinook/sales/new-customer")){
            Token userToken = tokenRepository.getByAuthToken(token[1]);
            Customer customer = customerRepository.getCustomerByEmail(userToken.getEmail());
            if(customer != null)
                return ResponseEntity.ok(customer);
        }
        Optional<Customer> customer = customerRepository.findById(customer1.getId());
        if(customer.isPresent()){
            customer.get().setId(customer1.getId());
            customer.get().setFirstName(customer1.getFirstName());
            customer.get().setLastName(customer1.getLastName());
            customer.get().setCompany(customer1.getCompany());
            customer.get().setAddress(customer1.getAddress());
            customer.get().setCity(customer1.getCity());
            customer.get().setState(customer1.getState());
            customer.get().setCountry(customer1.getCountry());
            customer.get().setPostalCode(customer1.getPostalCode());
            customer.get().setPhone(customer1.getPhone());
            customer.get().setFax(customer1.getFax());
            customer.get().setEmail(customer1.getEmail());
            final Customer updatesCustomer = customerRepository.save(customer1);
            return ResponseEntity.ok(updatesCustomer);
        } else
            
            return null;

    }
}







