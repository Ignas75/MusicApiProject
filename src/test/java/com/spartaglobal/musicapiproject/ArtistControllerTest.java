package com.spartaglobal.musicapiproject;

import com.spartaglobal.musicapiproject.pojo.ArtistId;
import com.spartaglobal.musicapiproject.requests.RequestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;

public class ArtistControllerTest {

    @Test
    public void getArtistIdTest() throws IOException, URISyntaxException, InterruptedException {
        ArtistId artist = RequestFactory.getArtistRequest(1);
        Assertions.assertEquals(1, artist.getId());
    }

    @Test
    public void createArtist() throws IOException, URISyntaxException, InterruptedException {
        ArtistId artist = RequestFactory.postArtist("ihKc6Ot7BE9MtptdVG5e");
        Assertions.assertEquals("George", artist.getName());
    }

    @Test
    public void updateArtist() throws IOException, URISyntaxException, InterruptedException {
        ArtistId artist = RequestFactory.putArtist("ihKc6Ot7BE9MtptdVG5e");
        Assertions.assertEquals("AC/Yefri", artist.getName());
    }

    @Test
    public void deleteArtist() throws IOException, URISyntaxException, InterruptedException {
        String response = RequestFactory.deleteArtist(276, "ihKc6Ot7BE9MtptdVG5e");
        Assertions.assertEquals("Artist deleted", response);
    }
}
