package com.spartaglobal.musicapiproject;


import com.spartaglobal.musicapiproject.entities.Album;
import com.spartaglobal.musicapiproject.pojo.AlbumId;
import com.spartaglobal.musicapiproject.requests.RequestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class AlbumControllerTest {
    // TODO: Delete album, create album, songs on album, buy album
    private static AlbumId album;
    private static AlbumId postAlbum;
    private static String deleteAlbum;
    private static String deleteAlbum2;
    private static AlbumId updateAlbum;


    // for album return album id, check that the actual methods do return the object

    @BeforeAll
    public static void trackRequests() throws IOException, InterruptedException, URISyntaxException{
        album = RequestFactory.getAlbumId(1);
        postAlbum= RequestFactory.createAlbumRequest("WMaFkaH5nPPUwyVZDAzq");
        deleteAlbum = RequestFactory.deleteAlbum(235, "WMaFkaH5nPPUwyVZDAzq");
        deleteAlbum2 = RequestFactory.deleteAlbum(postAlbum.getId(), "WMaFkaH5nPPUwyVZDAzq");
        updateAlbum = RequestFactory.updateAlbum("WMaFkaH5nPPUwyVZDAzq");
    }

    @Test
    @DisplayName("GET id for album_id = 1")
    public void getAlbum() {
        Assertions.assertEquals(1, album.getId());
    }

    @Test
    @DisplayName("GET title for album_id = 1")
    public void getAlbumTitle() {
        Assertions.assertEquals("For Those About To Rock We Salute You", album.getTitle());
    }

    @Test
    @DisplayName("GET artist Name for album_id = 1")
    public void getArtistIdFromAlbum() {
        Assertions.assertEquals("AC/DC", album.getArtistId().getName());
    }

    @Test
    @DisplayName("POST creating a new album")
    public void postAlbum() {
        Assertions.assertEquals("BUBBA", postAlbum.getTitle());
    }

    @Test
    @DisplayName("POST creating a new album")
    public void postAlbumGetArtistName() {
        Assertions.assertEquals("AC/DC", postAlbum.getArtistId().getName());
    }

    @Test
    @DisplayName("cant delete an album that contains purchased songs")
    public void deleteAlbum() {
        Assertions.assertEquals("Cannot delete album containing purchased songs", deleteAlbum);
    }

    @Test
    @DisplayName("can delete")
    public void deleteNewAlbum() {
        Assertions.assertEquals("Album deleted", deleteAlbum2);
    }

    @Test
    @DisplayName("get updated album")
    public void updateAlbum() {
        Assertions.assertEquals(7, updateAlbum.getId());
    }

    @Test
    @DisplayName("get updated album")
    public void updateAlbumGetName() {
        Assertions.assertEquals("AC/DC", updateAlbum.getArtistId().getName());
    }

    @Test
    @DisplayName("get updated album")
    public void updateAlbumGetTitle() {
        Assertions.assertEquals("Yefri", updateAlbum.getTitle());
    }





}
