package com.spartaglobal.musicapiproject;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spartaglobal.musicapiproject.pojo.AlbumId;
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

public class AlbumControllerTest {
    // TODO: Delete album, create album, songs on album, buy album

    @Test
    @DisplayName("GET request for album_id = 1")
    public void getTrack(){
        ObjectMapper mapper = new ObjectMapper();
        AlbumId album;
        try {
            album = mapper.readValue(new URL("http://localhost:8080/chinook/album?id=1"), AlbumId.class);
            Assertions.assertEquals(1, album.getId());
            Assertions.assertEquals("For Those About To Rock We Salute You", album.getTitle());
            Assertions.assertEquals(1, album.getArtistId().getId());
            Assertions.assertEquals("AC/DC", album.getArtistId().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Create album")
    public void createAlbum() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(new URI("http://localhost:8080/chinook/album/create"))
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/java/com/spartaglobal/musicapiproject/json/album.json")))
                .header("content-type", "application/json")
                .header("Authorization", "Basic ihKc6Ot7BE9MtptdVG5e")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(req,
                HttpResponse.BodyHandlers.ofString());
        String json = resp.body();
        ObjectMapper mapper = new ObjectMapper();
        AlbumId album = mapper.readValue(json, AlbumId.class);
        Assertions.assertEquals("BUBBA", album.getTitle());
        Assertions.assertEquals(1, album.getArtistId().getId());
    }
}
