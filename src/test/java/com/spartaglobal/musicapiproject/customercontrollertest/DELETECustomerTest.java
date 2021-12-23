package com.spartaglobal.musicapiproject.customercontrollertest;

import com.spartaglobal.musicapiproject.pojo.CustomerAdd;
import com.spartaglobal.musicapiproject.util.CustomerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DELETECustomerTest {
    static CustomerAdd customerAdd;

    @BeforeAll
    public static void jsonConv() {
        customerAdd = CustomerUtil.jsonConvDeleteCustomer("application/json");
    }

    @Test
    public void customerDeletedTest() {
        Assertions.assertTrue(customerAdd.getMessage().equals("Customer deleted"));
    }


    //check if the invoice has been created or not
}
