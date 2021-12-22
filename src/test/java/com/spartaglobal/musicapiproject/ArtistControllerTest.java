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
        HttpResponse<String> resp = ArtistRequest.postArtistRequest("jj11bpuFUxK2u4PmikEW");
        Assertions.assertEquals("Yefri", ArtistRequest.returnJsonBody(resp).getName());
    }

    @Test
    public void updateArtist() throws IOException, URISyntaxException, InterruptedException {
        //setting original value
        HttpResponse<String> resp = ArtistRequest.putArtistRequest("jj11bpuFUxK2u4PmikEW", 6, "Antônio Carlos Jobim");
        Assertions.assertEquals("Antônio Carlos Jobim", ArtistRequest.returnJsonBody(resp).getName());
        //reading the entry after updating
        HttpResponse<String> resp2 = ArtistRequest.putArtistRequest("jj11bpuFUxK2u4PmikEW", 6, "AC/DC");
        Assertions.assertEquals("AC/DC", ArtistRequest.returnJsonBody(resp2).getName());
    }

    // cant delete due to foreign keys, dependent on invoice line
//    @Test
//    public void deleteArtist() throws IOException, URISyntaxException, InterruptedException {
//        HttpResponse<String> resp = ArtistRequest.deleteArtistRequest("jj11bpuFUxK2u4PmikEW", 6);
//        Assertions.assertEquals(resp.statusCode(), 200);
//    }

}
