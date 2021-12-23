package com.spartaglobal.musicapiproject.requests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spartaglobal.musicapiproject.pojo.AlbumId;
import com.spartaglobal.musicapiproject.pojo.ArtistId;
import com.spartaglobal.musicapiproject.pojo.PlaylistPOJO;
import com.spartaglobal.musicapiproject.pojo.TrackPOJO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

public class RequestFactory {

    private static ObjectMapper mapper = new ObjectMapper();
    private static String jsonFilePath = "src/test/java/com/spartaglobal/musicapiproject/json/";

    private static HttpResponse<String> responseBuilder(String endpoint, Integer id, String token, String requestType, String jsonEndPoint)
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
            if (jsonEndPoint != null) {
                builder = builder.POST(HttpRequest.BodyPublishers.ofFile(Path.of
                        (jsonFilePath+jsonEndPoint)));
            } else {
                builder = builder.POST(HttpRequest.BodyPublishers.ofString(""));
            }
        } else if (requestType.equals("PUT")){
            builder = builder.PUT(HttpRequest.BodyPublishers.ofFile(Path.of
                    (jsonFilePath+jsonEndPoint)));
        } else {
            builder = builder.DELETE();
        }

        HttpRequest req = builder
                .header("content-type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Basic " + token)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(req,
                HttpResponse.BodyHandlers.ofString());

        return resp;
    }

    // Mappers
    private static TrackPOJO trackMapper(HttpResponse<String> resp) throws JsonProcessingException {
        TrackPOJO track = mapper.readValue(resp.body(), TrackPOJO.class);
        return track;
    }

    private static ArtistId artistMapper(HttpResponse<String> resp) throws JsonProcessingException {
        ArtistId artist = mapper.readValue(resp.body(), ArtistId.class);
        return artist;
    }

    private static PlaylistPOJO playlistMapper(HttpResponse<String> resp) throws JsonProcessingException {
        PlaylistPOJO playlist = mapper.readValue(resp.body(), PlaylistPOJO.class);
        return playlist;
    }

    private static AlbumId albumMapper(HttpResponse<String> resp) throws JsonProcessingException {
        AlbumId album = mapper.readValue(resp.body(), AlbumId.class);
        return album;
    }

    // Tracks
    public static TrackPOJO getTrackRequest(Integer id) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/track/read", id, null, "GET",
                null);
        return trackMapper(resp);
    }

    public static TrackPOJO postTrackRequest(String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/track/create", null, token, "POST",
                "track.json");
        return trackMapper(resp);
    }

    public static TrackPOJO putTrackRequest(String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/track/update", null, token, "PUT",
                "updateTrack.json");
        String json = resp.body();
        return trackMapper(resp);
    }

    public static String buyTrackRequest(Integer id, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/track/buy", id, token, "GET",
                null);
        return resp.body();
    }

    // Artist
    public static ArtistId getArtistRequest(Integer id) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/artist", id, null, "GET",
                null);
        return artistMapper(resp);
    }

    public static ArtistId postArtist(String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/artist/create", null, token, "POST",
                "artist.json");
        return artistMapper(resp);
    }

    public static ArtistId putArtist(String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/artist/update", null, token, "PUT",
                "updateArtist.json");
        return artistMapper(resp);
    }

    public static String deleteArtist(Integer id, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/artist/delete", id, token, "DELETE", null);
        return resp.body();
    }

    // Playlist
    public static PlaylistPOJO getPlaylist(Integer id) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/playlist", id, null, "GET", null);
        return playlistMapper(resp);
    }

    public static PlaylistPOJO postPlaylist(String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/playlist/create", null, token, "POST",
                "playlist.json");
        return playlistMapper(resp);
    }

    public static PlaylistPOJO putPlaylist(String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/playlist/update", null, token, "PUT",
                "updatePlaylist.json");
        return playlistMapper(resp);
    }

    public static String deletePlaylist(Integer id, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/playlist/delete", id, token, "DELETE", null);
        return resp.body();
    }

    public static String buyPlaylist(Integer id, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/playlist/buy", id, token, "POST", null);
        return resp.body();
    }

    // Album
    public static AlbumId getAlbumId(Integer id) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/album", id, null, "GET", null);
        return albumMapper(resp);
    }

    public static AlbumId createAlbumRequest(String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/album/create", null, token, "POST", "album.json");
        return albumMapper(resp);
    }

    public static String deleteAlbum(Integer id, String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/album/delete", id, token, "DELETE", null);
        return resp.body();
    }

    public static AlbumId updateAlbum(String token) throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> resp = responseBuilder("/chinook/album/update", null, token, "PUT",
                "updateAlbum.json");
        return albumMapper(resp);
    }
}
