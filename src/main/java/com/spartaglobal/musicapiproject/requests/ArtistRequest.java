package com.spartaglobal.musicapiproject.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spartaglobal.musicapiproject.pojo.ArtistId;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ArtistRequest {
    public static HttpResponse<String> getArtistRequest(Integer id) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(new URI("http://localhost:8080/chinook/artist?id=1"))
                .GET()
                .header("content-type", "application/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(req,
                HttpResponse.BodyHandlers.ofString());
        return resp;
    }

    public static HttpResponse<String> postArtistRequest(String token) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(new URI("http://localhost:8080/chinook/artist/create"))
                .POST(HttpRequest.BodyPublishers.ofString("{\n" +
                        "    \"name\": \"Yefri\"\n" +
                        "}"))
                .header("content-type", "application/json")
                .header("Authorization", "Basic " + token)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(req,
                HttpResponse.BodyHandlers.ofString());
        return resp;
    }

    public static HttpResponse<String> putArtistRequest(String token, int id, String name) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(new URI("http://localhost:8080/chinook/artist/update"))
                .PUT(HttpRequest.BodyPublishers.ofString("{\n" +
                        "    \"id\": " + id + ",\n" +
                        "    \"name\": \"" + name + "\"\n" +
                        "}"))
                .header("content-type", "application/json")
                .header("Authorization", "Basic " + token)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(req,
                HttpResponse.BodyHandlers.ofString());
        return resp;
    }

    public static HttpResponse<String> deleteArtistRequest(String token, int id) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(new URI("http://localhost:8080/chinook/artist/delete?id=" + id))
                .DELETE()
                .header("content-type", "application/json")
                .header("Authorization", "Basic " + token)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(req,
                HttpResponse.BodyHandlers.ofString());
        return resp;
    }

    public static ArtistId returnJsonBody(HttpResponse<String> resp) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArtistId artist = mapper.readValue(resp.body(), ArtistId.class);
        return artist;
    }

}
