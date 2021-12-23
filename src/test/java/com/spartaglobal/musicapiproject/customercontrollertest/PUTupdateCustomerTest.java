package com.spartaglobal.musicapiproject.customercontrollertest;

import com.spartaglobal.musicapiproject.pojo.Customer;
import com.spartaglobal.musicapiproject.pojo.CustomerAdd;
import com.spartaglobal.musicapiproject.util.CustomerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PUTupdateCustomerTest {

    static CustomerAdd customerAdd;
    static Customer customer;

    @BeforeAll
    public static void jsonConv() {
        customerAdd = CustomerUtil.jsonConvUpdateCustomer("application/json");
        customer = customerAdd.getCustomer();
    }

    @DisplayName("Given that customer detail has been updated, Return message of update")
    @Test
    public void customerUpdateTest() {
        Assertions.assertTrue(customerAdd.getMessage().equals("Account Updated"));
    }


    @DisplayName("Given that customer details has been updated, Return should be updated details")
    @Test
    public void customerAddressUpdateTest() {
        Assertions.assertTrue(customer.getAddress().equals("123 Another Fake Street"));
        Assertions.assertTrue(customer.getCity().equals("Fake City"));
        Assertions.assertTrue(customer.getState().equals("FC"));
        Assertions.assertTrue(customer.getCountry().equals("United Kingdom"));
        Assertions.assertTrue(customer.getPostalCode().equals("54321"));
        Assertions.assertTrue(customer.getPhone().equals("+44 123456789"));
        Assertions.assertNull(null, customer.getFax());
        Assertions.assertNull(null, customer.getCompany());
    }


    @DisplayName("Given that customer details has been updated, Return name should be same as before")
    @Test
    public void customerNameTest() {
        Assertions.assertTrue(customer.getFirstName().equals("John"));
        Assertions.assertTrue(customer.getLastName().equals("Doe"));
    }

    @DisplayName("Given that customer details has been updated, Return email should not be changed")
    @Test
    public void customerEmailCheckTest() {
        Assertions.assertTrue(customer.getEmail().equals("example@gmail.com"));
    }


}
