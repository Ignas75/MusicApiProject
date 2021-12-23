package com.spartaglobal.musicapiproject.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spartaglobal.musicapiproject.pojo.CustomerAdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class CustomerUtil {

    private static HttpResponse getCustomer(String contentType) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/chinook/customer/"))
                .header("content-type", contentType)
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
    public static CustomerAdd jsonConverter(String contentType) {
        HttpResponse<String> response = getCustomer(contentType);
        if (response != null) {
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


    private static HttpResponse createCustomer(String contentType) {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/chinook/customer/create"))
                    .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/java/com/spartaglobal/musicapiproject/json/newCustomer.json")))
                    .header("content-type", contentType)
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static CustomerAdd jsonConv(String contentType) {
        HttpResponse<String> response = createCustomer(contentType);
        if (response != null) {
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


    private static HttpResponse updateCustomer(String contentType) {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/chinook/customer/update"))
                    .PUT(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/java/com/spartaglobal/musicapiproject/json/updateCustomer.json")))
                    .header("content-type", contentType)
                    .header("Authorization", "Basic hWPoFo88nfVcrQUkv4EF")
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static CustomerAdd jsonConvUpdateCustomer(String contentType) {
        HttpResponse<String> response = updateCustomer(contentType);
        if (response != null) {
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


    private static HttpResponse deleteCustomer(String contentType) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/chinook/customer/delete"))
                .header("content-type", contentType)
                .DELETE()
                .header("Authorization", "Basic hWPoFo88nfVcrQUkv4EF")
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

    public static CustomerAdd jsonConvDeleteCustomer(String contentType) {
        HttpResponse<String> response = deleteCustomer(contentType);
        if (response != null) {
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


}
