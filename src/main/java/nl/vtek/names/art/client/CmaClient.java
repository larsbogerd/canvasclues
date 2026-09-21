package nl.vtek.names.art.client;

import nl.vtek.names.art.dto.CmaItem;
import nl.vtek.names.art.dto.CmaResponse;
import nl.vtek.names.art.source.ArtSource;
import nl.vtek.names.art.source.SourceArtwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/** Cleveland Museum of Art (CMA) open access API. */
@Component
public class CmaClient implements ArtSource {

    public static final String SOURCE_KEY = "cma";

    private static final Logger log = LoggerFactory.getLogger(CmaClient.class);

    private final RestClient restClient;

    public CmaClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://openaccess-api.clevelandart.org/api")
                .build();
    }

    @Override
    public String key() {
        return SOURCE_KEY;
    }

    /**
     * The CMA has no IIIF server, so there is no {@code {size}} to substitute — the caller
     * gets the one derivative that exists and scales it in the browser.
     */
    @Override
    public String imageUrlTemplate(String imageId) {
        return "/img/%s/%s".formatted(SOURCE_KEY, imageId);
    }

    @Override
    public List<SourceArtwork> fetchArtworks(int size) {
        return searchArtworks(size).data().stream()
                .filter(CmaClient::hasUsableImage)
                .map(CmaClient::toSourceArtwork)
                .limit(size)
                .toList();
    }

    private CmaResponse searchArtworks(int size) {
        int limit = size * 2;
        int maxSkip = 400 - limit;
        int skip = ThreadLocalRandom.current().nextInt(0, maxSkip);

        try {
            return restClient
                    .get()
                    .uri(uri -> uri
                            .path("/artworks")
                            .queryParam("cc0", 1)
                            .queryParam("has_image", 1)
                            .queryParam("type", "Painting")
                            .queryParam("currently_on_view", true)
                            .queryParam("skip", skip)
                            .queryParam("limit", limit)
                            .build())
                    .retrieve()
                    .body(CmaResponse.class);
        } catch (RestClientException e) {
            log.error("Cleveland Museum of Art API failed: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch artworks from Cleveland Museum of Art API", e);
        }
    }

    /**
     * The CMA serves one fixed derivative per artwork rather than IIIF sizes, and they run from
     * 243px to 1263px wide. The board renders at 500px, so narrower images arrive upscaled.
     */
    private static boolean hasUsableImage(CmaItem item) {
        CmaItem.Web web = item.images() == null ? null : item.images().web();
        return web != null
                && web.url() != null
                && item.accessionNumber() != null
                && parseWidth(web.width()) >= 500;
    }

    private static SourceArtwork toSourceArtwork(CmaItem item) {
        String culture = item.culture() == null || item.culture().isEmpty() ? null : item.culture().getFirst();
        return new SourceArtwork(
                "%s/%s".formatted(item.accessionNumber(), item.images().web().filename()),
                item.title(),
                artistDisplay(item, culture),
                item.creationDate(),
                item.technique(),
                culture,
                item.measurements(),
                item.department(),
                null,
                null,
                item.description()
        );
    }

    private static String artistDisplay(CmaItem item, String culture) {
        if (item.creators() != null && !item.creators().isEmpty()) {
            return item.creators().getFirst().description();
        }
        return culture;
    }

    private static int parseWidth(String width) {
        try {
            return width == null || width.isBlank() ? 0 : Integer.parseInt(width.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
