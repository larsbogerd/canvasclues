package nl.vtek.names.art.client;

import nl.vtek.names.art.source.SourceArtwork;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseCreator;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class MetClientTest {

    private static final String API = "https://collectionapi.metmuseum.org/public/collection/v1";

    private final RestClient.Builder builder = RestClient.builder();
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).ignoreExpectOrder(true).build();
    private final MetClient client = new MetClient(builder);

    @Test
    void fetchArtworks_skipsUnplayableAndUnreachableObjects() {
        server.expect(once(), requestTo(API + "/objects?departmentIds=11"))
                .andRespond(json("{\"total\": 5, \"objectIDs\": [1, 2, 3, 4, 5]}"));
        server.expect(once(), requestTo(API + "/objects/1")).andRespond(json(object(true,
                "https://images.metmuseum.org/CRDImages/ep/web-large/DT1.jpg", "Wheat Field", "Vincent van Gogh")));
        server.expect(once(), requestTo(API + "/objects/2")).andRespond(json(object(false,
                "https://images.metmuseum.org/CRDImages/ep/web-large/DT2.jpg", "Not public domain", "")));
        server.expect(once(), requestTo(API + "/objects/3")).andRespond(json(object(true,
                "https://elsewhere.org/DT3.jpg", "Foreign host", "")));
        server.expect(once(), requestTo(API + "/objects/4")).andRespond(json(object(true,
                "https://images.metmuseum.org/CRDImages/ep/web-large/DT4.jpg", " ", "")));
        server.expect(once(), requestTo(API + "/objects/5")).andRespond(withServerError());

        List<SourceArtwork> artworks = client.fetchArtworks(4);

        server.verify();
        assertThat(artworks).singleElement().satisfies(artwork -> {
            assertThat(artwork.imageId()).isEqualTo("CRDImages/ep/web-large/DT1.jpg");
            assertThat(artwork.title()).isEqualTo("Wheat Field");
            assertThat(artwork.artistDisplay()).isEqualTo("Vincent van Gogh");
            assertThat(artwork.dateDisplay()).isNull();
        });
    }

    @Test
    void fetchArtworks_withoutAnArtistFallsBackToCulture() {
        server.expect(once(), requestTo(API + "/objects?departmentIds=11"))
                .andRespond(json("{\"total\": 1, \"objectIDs\": [1]}"));
        server.expect(once(), requestTo(API + "/objects/1")).andRespond(json("""
                {"title": "Portrait", "artistDisplayName": "", "culture": "Flemish", "objectDate": "",
                 "isPublicDomain": true,
                 "primaryImageSmall": "https://images.metmuseum.org/CRDImages/ep/web-large/DT9.jpg"}
                """));

        assertThat(client.fetchArtworks(1)).singleElement()
                .extracting(SourceArtwork::artistDisplay).isEqualTo("Flemish");
    }

    private static String object(boolean publicDomain, String image, String title, String artist) {
        return """
                {"title": "%s", "artistDisplayName": "%s", "isPublicDomain": %s, "primaryImageSmall": "%s"}
                """.formatted(title, artist, publicDomain, image);
    }

    private static ResponseCreator json(String body) {
        return withSuccess(body, MediaType.APPLICATION_JSON);
    }
}
