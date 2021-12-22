package com.spartaglobal.musicapiproject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spartaglobal.musicapiproject.pojo.Customer;
import com.spartaglobal.musicapiproject.pojo.CustomerAdd;
import org.assertj.core.api.AssertDelegateTarget;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CustomerControllerTest {

    private static CustomerAdd customerAdd;

    public static HttpResponse getCustomer(){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/chinook/customer/"))
                .header("Authorization", "Basic XJALyZSsaJnWWvGTqQQQ")
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    //convert Json
    public static CustomerAdd jsonConverter(){
        HttpResponse<String> response = getCustomer();
        if (response!=null){
            String json = response.body();
            ObjectMapper mapper = new ObjectMapper();
            try {
                CustomerAdd customerAdd = mapper.readValue(json, CustomerAdd.class);
                return customerAdd;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @BeforeAll
    public static void getJson(){
        customerAdd = jsonConverter();
    }


    @Test
    public void getCustomerByTheirAuthToken(){
        Assertions.assertEquals(1,customerAdd.getCustomer().getId());
    }




    @Test
    public void createCustomerTest(){

    }



}
