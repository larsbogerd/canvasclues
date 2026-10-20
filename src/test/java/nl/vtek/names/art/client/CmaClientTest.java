package nl.vtek.names.art.client;

import nl.vtek.names.art.source.SourceArtwork;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class CmaClientTest {

    private final RestClient.Builder builder = RestClient.builder();
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
    private final CmaClient client = new CmaClient(builder);

    @Test
    void fetchArtworks_asksForPaintingsOnViewAndKeepsOnlyBoardSizedImages() {
        server.expect(requestTo(startsWith("https://openaccess-api.clevelandart.org/api/artworks")))
                .andExpect(method(HttpMethod.GET))
                .andExpect(queryParam("type", "Painting"))
                .andExpect(queryParam("currently_on_view", "true"))
                .andExpect(queryParam("cc0", "1"))
                .andExpect(queryParam("limit", "4"))
                .andRespond(withSuccess("""
                        {"data": [
                          {"accession_number": "1916.1", "title": "Wide enough",
                           "creation_date": "1890", "technique": "oil on canvas", "department": "Painting",
                           "culture": ["France"], "creators": [{"description": "Claude Monet (French)"}],
                           "images": {"web": {"url": "https://cdn/1916.1_web.jpg", "width": "893",
                                              "filename": "1916.1_web.jpg"}}},
                          {"accession_number": "1916.2", "title": "Too narrow",
                           "images": {"web": {"url": "https://cdn/1916.2_web.jpg", "width": "243",
                                              "filename": "1916.2_web.jpg"}}},
                          {"accession_number": "1916.3", "title": "No image", "images": null},
                          {"accession_number": "1916.4", "title": "Anonymous", "culture": ["Japan"],
                           "creators": [],
                           "images": {"web": {"url": "https://cdn/1916.4_web.jpg", "width": " 500 ",
                                              "filename": "1916.4_web.jpg"}}}
                        ]}
                        """, MediaType.APPLICATION_JSON));

        List<SourceArtwork> artworks = client.fetchArtworks(2);

        server.verify();
        assertThat(artworks).extracting(SourceArtwork::title).containsExactly("Wide enough", "Anonymous");
        assertThat(artworks.getFirst().imageId()).isEqualTo("1916.1/1916.1_web.jpg");
        assertThat(artworks.getFirst().artistDisplay()).isEqualTo("Claude Monet (French)");
        assertThat(artworks.getLast().artistDisplay()).isEqualTo("Japan");
    }

    @Test
    void imageUrlTemplate_pointsAtTheSameOriginProxy() {
        assertThat(client.imageUrlTemplate("1916.1/1916.1_web.jpg")).isEqualTo("/img/cma/1916.1/1916.1_web.jpg");
    }
}
