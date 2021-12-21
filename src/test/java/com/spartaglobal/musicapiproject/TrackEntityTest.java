package com.spartaglobal.musicapiproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spartaglobal.musicapiproject.pojo.TrackPOJO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class TrackEntityTest {
    @Test
    @DisplayName("GET request for track_id = 1")
    public void getTrack(){
        ObjectMapper mapper = new ObjectMapper();
        TrackPOJO track;
        try {
            track = mapper.readValue(new URL("http://localhost:8080/chinook/track?id=1"), TrackPOJO.class);
            Assertions.assertEquals(1, track.getId());
            Assertions.assertEquals("For Those About To Rock (We Salute You)", track.getName());
            Assertions.assertEquals("For Those About To Rock We Salute You", track.getAlbumId().getTitle());
            Assertions.assertEquals("Rock", track.getGenreId().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addNewFilmHttpClientVersion() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(new URI("http://localhost:8080/chinook/track/add"))
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/java/com/spartaglobal/musicapiproject/json/track.json")))
                .header("content-type", "application/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(req,
                HttpResponse.BodyHandlers.ofString());
        String json = resp.body();
        System.out.println(resp);
        ObjectMapper mapper = new ObjectMapper();
        TrackPOJO track = mapper.readValue(json, TrackPOJO.class);
        System.out.println("Inserted New track with id = " + track.getId());
        Assertions.assertEquals("Test tracks", track.getName());
        Assertions.assertEquals("AC/DC", track.getAlbumId().getTitle());
        Assertions.assertEquals("Angus Young", track.getComposer());
    }
}
