package nl.vtek.names.art.client;

import nl.vtek.names.art.dto.MetItem;
import nl.vtek.names.art.dto.MetResponse;
import nl.vtek.names.art.source.ArtSource;
import nl.vtek.names.art.source.SourceArtwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** The Metropolitan Museum of Art collection API. */
@Component
public class MetClient implements ArtSource {

    public static final String SOURCE_KEY = "met";

    private static final Logger log = LoggerFactory.getLogger(MetClient.class);

    private static final String IMAGE_HOST = "https://images.metmuseum.org/";

    private final RestClient restClient;

    public MetClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://collectionapi.metmuseum.org/public/collection/v1")
                .build();
    }

    @Override
    public String key() {
        return SOURCE_KEY;
    }

    /** The Met serves two fixed derivatives rather than IIIF sizes, so there is no size to fill in. */
    @Override
    public String imageUrlTemplate(String imageId) {
        return "/img/%s/%s".formatted(SOURCE_KEY, imageId);
    }

    @Override
    public List<SourceArtwork> fetchArtworks(int size) {
        return searchArtworks(size).stream()
                .filter(MetClient::isPlayable)
                .map(MetClient::toSourceArtwork)
                .limit(size)
                .toList();
    }

    private List<MetItem> searchArtworks(int size) {
        // Department 11 is European Paintings: ~2,600 objects, almost all actual paintings.
        MetResponse listing = restClient
                .get()
                .uri(uri -> uri.path("/objects").queryParam("departmentIds", 11).build())
                .retrieve()
                .body(MetResponse.class);

        if (listing == null || listing.objectIds() == null) {
            throw new RuntimeException("Failed to fetch object ids from Met API");
        }

        // A fifth of the department is not public domain or has no image, and every id here
        // costs a request, so take a quarter over what is asked for and no more.
        List<Integer> ids = new ArrayList<>(listing.objectIds());
        Collections.shuffle(ids);

        return ids.subList(0, Math.min(size + (size / 4), ids.size()))
                .parallelStream()
                .map(this::fetchObject)
                .filter(Objects::nonNull)
                .toList();
    }

    /** One unreachable object should cost a card, not the whole board. */
    private MetItem fetchObject(int objectId) {
        try {
            return restClient.get().uri("/objects/{id}", objectId).retrieve().body(MetItem.class);
        } catch (RestClientException e) {
            log.warn("Met object {} failed: {}", objectId, e.getMessage());
            return null;
        }
    }

    private static boolean isPlayable(MetItem item) {
        return item.isPublicDomain()
                && item.primaryImageSmall() != null
                && item.primaryImageSmall().startsWith(IMAGE_HOST)
                && item.title() != null
                && !item.title().isBlank();
    }

    private static SourceArtwork toSourceArtwork(MetItem item) {
        String culture = blankToNull(item.culture());
        String artist = blankToNull(item.artistDisplayName());
        return new SourceArtwork(
                item.primaryImageSmall().substring(IMAGE_HOST.length()),
                item.title(),
                artist == null ? culture : artist,
                blankToNull(item.objectDate()),
                blankToNull(item.medium()),
                culture,
                blankToNull(item.dimensions()),
                blankToNull(item.department()),
                null,
                null,
                null
        );
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }
}
