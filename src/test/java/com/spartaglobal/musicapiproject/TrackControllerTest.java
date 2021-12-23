package com.spartaglobal.musicapiproject;

import com.spartaglobal.musicapiproject.pojo.TrackPOJO;
import com.spartaglobal.musicapiproject.requests.RequestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class TrackControllerTest {
    //TODO - track/delete <- delete isn't finished yet :(
    private static TrackPOJO track;
    private static TrackPOJO postTrack;
    private static TrackPOJO putTrack;
    private static String buyTrack;

    @BeforeAll
    public static void trackRequests() throws IOException, InterruptedException, URISyntaxException{
        track = RequestFactory.getTrackRequest(1);
        postTrack= RequestFactory.postTrackRequest("ihKc6Ot7BE9MtptdVG5e");
        putTrack = RequestFactory.putTrackRequest("ihKc6Ot7BE9MtptdVG5e");
        buyTrack = RequestFactory.buyTrackRequest(1, "JJVXUgNu6zoGeeyZsYa1");
    }

    // Customer, Staff, Admin, Invalid Id, Valid Id, Invalid token, Valid token
    @Test
    @DisplayName("Customer successfully buys track")
    public void buyTrack() {
        Assertions.assertEquals("Invoice(s) created", buyTrack);
    }

    @Test
    @DisplayName("GET request for track_id = 1")
    public void getTrack() {
        Assertions.assertEquals(1, track.getId());
    }

    @Test
    @DisplayName("POST creating a new track")
    public void postTrack() {
        Assertions.assertEquals("See Right Through You Mate", postTrack.getName());
    }

    @Test
    @DisplayName("PUT track name")
    public void putTrackName() {
        Assertions.assertEquals("MacBeth", putTrack.getName());
    }

    @Test
    @DisplayName("PUT track title")
    public void putTrackAlbum() {
        Assertions.assertEquals("Greatest Kiss", putTrack.getAlbumId().getTitle());
    }

    @Test
    @DisplayName("PUT track composer")
    public void putTrackComposer() {
        Assertions.assertEquals("S. Penridge, Bob Ezrin, Peter Criss", putTrack.getComposer());
    }
}
