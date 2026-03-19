package nl.vtek.names.art.client;

import nl.vtek.names.art.dto.ArticResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ArticClient {

    private static final Logger log = LoggerFactory.getLogger(ArticClient.class);

    private final RestClient restClient;

    public ArticClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.artic.edu/api/v1")
                .build();
    }

    public ArticResponse searchArtworks(int size) {
        int maxFrom = 1000 - size;
        Map<String, Object> query = Map.of(
                "query", Map.of(
                        "bool", Map.of(
                                "must", List.of(
                                        Map.of("term", Map.of("is_public_domain", true)),
                                        Map.of("term", Map.of("is_on_view", true)),
                                        Map.of("exists", Map.of("field", "image_id"))
                                )
                        )
                ),
                "from", ThreadLocalRandom.current().nextInt(0, maxFrom),
                "size", size
        );

        try {
            return restClient
                    .post()
                    .uri("/artworks/search?fields={fields}", "id,image_id,title,artist_display,date_display")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(query)
                    .retrieve()
                    .body(ArticResponse.class);
        } catch (RestClientException e) {
            log.error("Artic API failed: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch artworks from Artic API", e);
        }
    }
}
