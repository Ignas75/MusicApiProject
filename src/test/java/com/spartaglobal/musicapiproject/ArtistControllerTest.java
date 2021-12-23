package com.spartaglobal.musicapiproject;

import com.spartaglobal.musicapiproject.pojo.ArtistId;
import com.spartaglobal.musicapiproject.requests.ArtistRequest;
import com.spartaglobal.musicapiproject.requests.RequestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

public class ArtistControllerTest {

    @Test
    public void getArtistIdTest() throws IOException, URISyntaxException, InterruptedException {
        ArtistId artist = RequestBuilder.getArtistRequest(1);
        Assertions.assertEquals(1, artist.getId());
    }

    @Test
    public void createArtist() throws IOException, URISyntaxException, InterruptedException {
        ArtistId artist = RequestBuilder.postArtist("ihKc6Ot7BE9MtptdVG5e");
        Assertions.assertEquals("George", artist.getName());
    }

    @Test
    public void updateArtist() throws IOException, URISyntaxException, InterruptedException {
        ArtistId artist = RequestBuilder.putArtist("ihKc6Ot7BE9MtptdVG5e");
        Assertions.assertEquals("AC/Yefri", artist.getName());
    }

    @Test
    public void deleteArtist() throws IOException, URISyntaxException, InterruptedException {
        String response = RequestBuilder.deleteArtist(276, "ihKc6Ot7BE9MtptdVG5e");
        Assertions.assertEquals("Artist deleted", response);
    }
}
