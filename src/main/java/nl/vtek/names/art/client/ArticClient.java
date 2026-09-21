package nl.vtek.names.art.client;

import nl.vtek.names.art.dto.ArticDto;
import nl.vtek.names.art.dto.ArticResponse;
import nl.vtek.names.art.source.ArtSource;
import nl.vtek.names.art.source.SourceArtwork;
import nl.vtek.names.art.util.IiifUrlBuilder;
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
public class ArticClient implements ArtSource {

    public static final String SOURCE_KEY = "artic";

    private static final Logger log = LoggerFactory.getLogger(ArticClient.class);

    private static final String IIIF_BASE_PATH = "/iiif/2";

    private static final String FIELDS = String.join(",",
            "id",
            "image_id",
            "title",
            "artist_display",
            "date_display",
            "medium_display",
            "place_of_origin",
            "dimensions",
            "department_title",
            "style_title",
            "artwork_type_title",
            "short_description"
    );

    private final RestClient restClient;

    public ArticClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.artic.edu/api/v1")
                .build();
    }

    @Override
    public String key() {
        return SOURCE_KEY;
    }

    @Override
    public String imageUrlTemplate(String imageId) {
        return IiifUrlBuilder.template(IIIF_BASE_PATH, imageId);
    }

    @Override
    public List<SourceArtwork> fetchArtworks(int size) {
        return searchArtworks(size).pulledData().stream()
                .map(ArticClient::toSourceArtwork)
                .toList();
    }

    private ArticResponse searchArtworks(int size) {
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
                    .uri("/artworks/search?fields={fields}", FIELDS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(query)
                    .retrieve()
                    .body(ArticResponse.class);
        } catch (RestClientException e) {
            log.error("Artic API failed: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch artworks from Artic API", e);
        }
    }

    private static SourceArtwork toSourceArtwork(ArticDto dto) {
        return new SourceArtwork(
                dto.id() == null ? null : dto.id().toString(),
                dto.title(),
                dto.artistDisplay(),
                dto.dateDisplay(),
                dto.mediumDisplay(),
                dto.placeOfOrigin(),
                dto.dimensions(),
                dto.departmentTitle(),
                dto.styleTitle(),
                dto.artworkTypeTitle(),
                dto.shortDescription()
        );
    }
}
