package nl.vtek.names.art.client;

import nl.vtek.names.art.dto.ArticResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ArticClient {

    private final RestClient restClient;

    public ArticClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.artic.edu/api/v1")
                .build();
    }

    // Optional query filters:
    // &query[term][artwork_type_title]=Painting
    // &query[term][department_title]=Modern Art
    // &query[term][classification_title]=painting
    // &query[term][artist_title]=Claude Monet
    // &q=landscape  (free-text search)

    public ArticResponse getArtworks(int page, int limit) {
        return restClient
                .get()
                .uri("/artworks?page={page}&limit={limit}&fields=id,image_id,title,artist_display,date_display" +
                        "&query[term][is_public_domain]=true" +
                        "&query[exists][field]=image_id", page, limit)
                .retrieve()
                .body(ArticResponse.class);
    }
}
