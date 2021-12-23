package com.spartaglobal.musicapiproject.customercontrollertest;

import com.spartaglobal.musicapiproject.pojo.Customer;
import com.spartaglobal.musicapiproject.pojo.CustomerAdd;
import com.spartaglobal.musicapiproject.util.CustomerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class POSTCreateCustomerControllerTest {

    static CustomerAdd customerAdd;
    static Customer customer;


    @BeforeAll
    public static void jsonConv() {
        customerAdd = CustomerUtil.jsonConv("application/json");
        customer = customerAdd.getCustomer();
    }


    @DisplayName("Given that customer entered their details and account created, Return message of account created")
    @Test
    public void creatNewCustomerTest() {
        Assertions.assertTrue(customerAdd.getMessage().equals("Account Created"));
    }

    @DisplayName("Given that customer entered their details and account created, Return should have their first name and last name")
    @Test
    public void getCustomerNamesTest() {
        Assertions.assertTrue(customer.getFirstName().equals("John"));
        Assertions.assertTrue(customer.getLastName().equals("Doe"));
    }

    @DisplayName("Given that customer entered their details and account created, Return company name")
    @Test
    public void getCustomerCompanyNameTest() {
        Assertions.assertNull(null, customer.getCompany());
    }

    @DisplayName("Given that customer has entered their details and account been created, Return customer email address")
    @Test
    public void getCustomerEmailAddressTest() {
        Assertions.assertTrue(customer.getEmail().equals("example@gmail.com"));
    }

    @DisplayName("Given that customer has entered their details and account been created, Return should be customer address")
    @Test
    public void getCustomerAddressTest() {
        Assertions.assertTrue(customer.getAddress().equals("123 Fake Street"));
        Assertions.assertTrue(customer.getCity().equals("Fake City"));
        Assertions.assertTrue(customer.getState().equals("FC"));
        Assertions.assertTrue(customer.getCountry().equals("United Kingdom"));
        Assertions.assertTrue(customer.getPostalCode().equals("12345"));
    }

    @DisplayName("Given that customer has entered their details and account been created, Return phone number and fax number")
    @Test
    public void getCustomerPhoneAndFaxTest() {
        Assertions.assertTrue(customer.getPhone().equals("+44 123456789"));
        Assertions.assertNull(null, customer.getFax());
    }

    @DisplayName("Given that customer has entered their details and account been created, Return support rep id")
    @Test
    public void getCustomerSupportRepIdTest() {
        Assertions.assertTrue(customer.getSupportRepId().equals(3));
    }

}
