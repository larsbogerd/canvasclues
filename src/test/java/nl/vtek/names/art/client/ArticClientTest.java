package nl.vtek.names.art.client;

import nl.vtek.names.art.source.SourceArtwork;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class ArticClientTest {

    private static final String IMAGE_ID = "a38e2828-ec6f-ece1-a30f-70243449197b";

    private final RestClient.Builder builder = RestClient.builder();
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final ArticClient client = new ArticClient(builder);

    @Test
    void fetchArtworks_searchesPublicDomainWorksOnViewAndMapsThem() {
        server.expect(requestTo(startsWith("https://api.artic.edu/api/v1/artworks/search")))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.query.bool.must[0].term.is_public_domain").value(true))
                .andRespond(withSuccess("""
                        {"data": [
                          {"image_id": "%s", "title": "Stacks of Wheat", "artist_display": "Claude Monet",
                           "style_title": "Impressionism"},
                          {"image_id": null, "title": "No image"}
                        ]}
                        """.formatted(IMAGE_ID), MediaType.APPLICATION_JSON));

        List<SourceArtwork> artworks = client.fetchArtworks(2);

        server.verify();
        assertThat(artworks).extracting(SourceArtwork::imageId).containsExactly(IMAGE_ID, null);
        assertThat(artworks.getFirst().styleTitle()).isEqualTo("Impressionism");
    }

    @Test
    void warmImage_requestsTheBoardSizeWithTheRefererCloudflareWants() {
        server.expect(requestTo("https://www.artic.edu/iiif/2/%s/full/500,/0/default.jpg".formatted(IMAGE_ID)))
                .andExpect(header(HttpHeaders.REFERER, "https://www.artic.edu/"))
                .andRespond(withSuccess(new byte[] {1}, MediaType.IMAGE_JPEG));

        assertThat(client.warmImage(IMAGE_ID)).isTrue();
        server.verify();
    }

    @Test
    void warmImage_reissuedImageId_keepsTheArtworkOutOfThePool() {
        server.expect(requestTo(startsWith("https://www.artic.edu/iiif/2/"))).andRespond(withResourceNotFound());

        assertThat(client.warmImage(IMAGE_ID)).isFalse();
    }

    @Test
    void imageUrlTemplate_isIiifWithASizePlaceholder() {
        assertThat(client.imageUrlTemplate(IMAGE_ID))
                .isEqualTo("/img/artic/%s/full/{size}/0/default.jpg".formatted(IMAGE_ID));
    }
}
