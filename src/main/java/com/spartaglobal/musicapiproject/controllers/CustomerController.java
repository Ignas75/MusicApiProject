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
 * Please add following lines of sql query into the database after running the endpointpermissions query from AuthorizationService class
 *
  INSERT INTO endpointpermissions (url, IsForCustomer, IsForStaff, IsForAdmins) VALUES ("chinook/sales/customer", 0,1,1);
  INSERT INTO endpointpermissions (url, IsForCustomer, IsForStaff, IsForAdmins) VALUES ("chinook/customer", 1,0,0);
  INSERT INTO endpointpermissions (url, IsForCustomer, IsForStaff, IsForAdmins) VALUES ("chinook/sales/update", 0,1,1);
  INSERT INTO endpointpermissions (url, IsForCustomer, IsForStaff, IsForAdmins) VALUES ("chinook/sales/delete/customer", 0,0,1);
  INSERT INTO endpointpermissions (url, IsForCustomer, IsForStaff, IsForAdmins) VALUES ("", 0,0,0);
 *
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

    //**TODO Check this if we need this or not */
    @DeleteMapping(value = "chinook/sales/delete/customer")
    public ResponseEntity<?> deleteCustomerByCustomerID(@RequestParam Integer customerID, @RequestHeader("Authorization") String authToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        String token[] = authToken.split(" ");
        if (aS.isAuthorizedForAction(token[1], "chinook/sales/delete/customer")) {
            if (customerRepository.existsById(customerID)){
                Customer customer = customerRepository.getById(customerID);
                customerRepository.delete(customer);
            }
        }
        return new ResponseEntity<>("{\"message\":\"Invalid Request\"}", headers, HttpStatus.BAD_REQUEST);
    }



    @PatchMapping(value="chinook/sales/update")
    public ResponseEntity<?> updateCustomerByEmailAddress( @RequestParam Customer customer1, @RequestHeader("Authorization") String authToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type","application/json");
        String token[] = authToken.split("");
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
        String message = "{\"messsage\" : \"Customer Update\", \"film\":";
        return null;
    }
}







