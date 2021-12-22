package com.spartaglobal.musicapiproject.requests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spartaglobal.musicapiproject.pojo.ArtistId;
import com.spartaglobal.musicapiproject.pojo.TrackPOJO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class RequestBuilder {

    private static ObjectMapper mapper = new ObjectMapper();

    private static HttpResponse<String> trackRequest(String endpoint, Integer id, String token, String requestType)
            throws IOException, InterruptedException, URISyntaxException {
        StringBuilder url = new StringBuilder("http://localhost:8080");
        url.append(endpoint);
        if (id != null) {
            url.append("?id="+id);
        }
        HttpRequest.Builder builder = HttpRequest
                .newBuilder()
                .uri(new URI(url.toString()));


        if (requestType.equals("GET")) {
            builder = builder.GET();
        } else if (requestType.equals("POST")) {
            System.out.println(token);
            builder = builder.POST(HttpRequest.BodyPublishers.ofFile(Path.of
                    ("src/test/java/com/spartaglobal/musicapiproject/json/track.json")));
        } else {
            builder = builder.PUT(HttpRequest.BodyPublishers.ofFile(Path.of
                    ("src/test/java/com/spartaglobal/musicapiproject/json/updateTrack.json")));
        }

        HttpRequest req = builder
                .header("content-type", "application/json")
                .header("Authorization", "Basic " + token)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(req,
                HttpResponse.BodyHandlers.ofString());

        return resp;
    }

    private static TrackPOJO trackMapper(String json) {
        TrackPOJO track = null;
        try {
            track = mapper.readValue(json, TrackPOJO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return track;
    }

    private static ArtistId artistMapper(HttpResponse<String> resp) throws JsonProcessingException {
        ArtistId artist = mapper.readValue(resp.body(), ArtistId.class);
        return artist;
    }

    public static TrackPOJO getTrackRequest(Integer id) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = trackRequest("/chinook/track/read", id, null, "GET");
        String json = resp.body();
        return trackMapper(json);
    }

    public static TrackPOJO postTrackRequest(String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = trackRequest("/chinook/track/create", null, token, "POST");
        String json = resp.body();
        return trackMapper(json);
    }

    public static TrackPOJO putTrackRequest(String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = trackRequest("/chinook/track/update", null, token, "PUT");
        String json = resp.body();
        return trackMapper(json);
    }

    public static String buyTrackRequest(Integer id, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = trackRequest("/chinook/track/buy", id, token, "GET");
        return resp.body();
    }

    public static ArtistId getArtistRequest(Integer id) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = trackRequest("/chinook/artist", id, null, "GET");
        return artistMapper(resp);
    }

}
