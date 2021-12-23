package com.spartaglobal.musicapiproject.customercontrollertest;


import com.spartaglobal.musicapiproject.pojo.Customer;
import com.spartaglobal.musicapiproject.pojo.CustomerAdd;
import com.spartaglobal.musicapiproject.util.CustomerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerControllerTest {

    static CustomerUtil customerUtil = new CustomerUtil();
    private static CustomerAdd customerAdd;
    private static Customer customer;

    @BeforeAll
    public static void getJson() {
        customerAdd = customerUtil.jsonConverter("application/json");
        if (customerAdd.getCustomer() != null) {
            customer = customerAdd.getCustomer();
        }
    }

    @DisplayName("1.1 Given the customer authentication token, the return should be have the customer ID of 1")
    @Test
    public void getCustomerByTheirAuthToken() {
        Assertions.assertEquals(1, customerAdd.getCustomer().getId());
    }

    @DisplayName("1.2 Given the customer authentication token, Return first name and last name")
    @Test
    public void getCustomerNameTest() {
        Assertions.assertEquals("Luís", customer.getFirstName());
        Assertions.assertEquals("Gonçalves", customer.getLastName());
    }

    @DisplayName("1.3 Given the customer authentication token, Return company name")
    @Test
    public void getCustomerCompanyNameTest() {
        Assertions.assertEquals("Embraer - Empresa Brasileira de Aeronáutica S.A.", customer.getCompany());
    }

    @DisplayName("1.4 Given the customer Auth Token, Return customer email address")
    @Test
    public void getCustomerEmailAddressTest() {
        Assertions.assertEquals("luisg@embraer.com.br", customer.getEmail());
    }

    @DisplayName("1.5 Given the Customer auth Token, Return should be customer address")
    @Test
    public void getCustomerAddressTest() {
        Assertions.assertTrue(customer.getAddress().equals("Av. Brigadeiro Faria Lima, 2170"));
        Assertions.assertTrue(customer.getCity().equals("São José dos Campos"));
        Assertions.assertTrue(customer.getState().equals("SP"));
        Assertions.assertTrue(customer.getCountry().equals("Brazil"));
        Assertions.assertTrue(customer.getPostalCode().equals("12227-000"));
    }

    @DisplayName("1.6 Given the Customer auth Token, Return phone number and fax number")
    @Test
    public void getCustomerPhoneAndFaxTest() {
        Assertions.assertTrue(customer.getPhone().equals("+55 (12) 3923-5555"));
        Assertions.assertTrue(customer.getFax().equals("+55 (12) 3923-5566"));
    }

    @DisplayName("1.7 Given the Customer Auth Token, Return support rep id")
    @Test
    public void getCustomerSupportRepIdTest() {
        Assertions.assertTrue(customer.getSupportRepId().equals(3));
    }

    @DisplayName("1.8 Given the customer Auth Token, Return message")
    @Test
    public void getCustomerMessageTest() {
        Assertions.assertTrue(customerAdd.getMessage().equals("Your Account Details"));
    }


}
