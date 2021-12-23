package com.spartaglobal.musicapiproject;

import com.spartaglobal.musicapiproject.pojo.ArtistId;
import com.spartaglobal.musicapiproject.pojo.PlaylistPOJO;
import com.spartaglobal.musicapiproject.requests.RequestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class PlaylistControllerTest {
    @Test
    public void getArtistIdTest() throws IOException, URISyntaxException, InterruptedException {
        ArtistId artist = RequestFactory.getArtistRequest(1);
        Assertions.assertEquals(1, artist.getId());
    }

    @Test
    public void getPlaylistTest() throws IOException, URISyntaxException, InterruptedException {
        PlaylistPOJO playlist = RequestFactory.getPlaylist(1);
        Assertions.assertEquals(1, playlist.getId());
    }

    @Test
    public void deletePlaylistTest() throws IOException, URISyntaxException, InterruptedException {
        String response = RequestFactory.deletePlaylist(2, "ihKc6Ot7BE9MtptdVG5e");
        Assertions.assertEquals("Playlist deleted", response);
    }

    @Test
    public void buyPlaylistTest() throws IOException, URISyntaxException, InterruptedException {
        String response = RequestFactory.buyPlaylist(1, "JJVXUgNu6zoGeeyZsYa1");
        Assertions.assertEquals("Invoice(s) created", response);
    }
}
