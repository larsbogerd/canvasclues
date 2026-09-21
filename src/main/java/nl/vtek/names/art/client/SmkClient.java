package nl.vtek.names.art.client;

import nl.vtek.names.art.dto.SmkItem;
import nl.vtek.names.art.dto.SmkResponse;
import nl.vtek.names.art.source.ArtSource;
import nl.vtek.names.art.source.SourceArtwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class SmkClient implements ArtSource {

    public static final String SOURCE_KEY = "smk";

    private static final Logger log = LoggerFactory.getLogger(SmkClient.class);

    /**
     * SMK returns a whole image URL where Artic returns a bare id, so the prefix is stripped
     * back off to get something we can store and rebuild from.
     */
    private static final String IIIF_URL_PREFIX = "https://iip.smk.dk/iiif/jp2/";

    /**
     * Paintings only: the unrestricted pool is mostly print series, and a random window of it
     * comes back as twenty near-identical engravings from the same series — an unplayable board.
     *
     * <p>{@code image_hq} is what guarantees an IIIF id. A fifth of {@code has_image} records
     * have none, and they cluster, so without this filter a board silently comes up short.
     */
    private static final String FILTERS =
            "[has_image:true],[public_domain:true],[object_names:maleri],[image_hq:true]";

    /** The filtered pool holds ~2,000 works; stay inside it when picking a random offset. */
    private static final int MAX_OFFSET = 1900;

    /** Small margin in case a record still arrives without a usable id. */
    private static final int ROW_BUFFER = 8;

    private final RestClient restClient;

    public SmkClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.smk.dk/api/v1")
                .build();
    }

    @Override
    public String key() {
        return SOURCE_KEY;
    }

    @Override
    public List<SourceArtwork> fetchArtworks(int size) {
        return search(size + ROW_BUFFER).items().stream()
                .map(SmkClient::toSourceArtwork)
                .filter(artwork -> artwork.imageId() != null)
                .limit(size)
                .toList();
    }

    private SmkResponse search(int rows) {
        int offset = ThreadLocalRandom.current().nextInt(0, Math.max(1, MAX_OFFSET - rows));

        try {
            return restClient
                    .get()
                    .uri(uri -> uri
                            .path("/art/search/")
                            .queryParam("keys", "*")
                            .queryParam("filters", FILTERS)
                            .queryParam("offset", offset)
                            .queryParam("rows", rows)
                            .build())
                    .retrieve()
                    .body(SmkResponse.class);
        } catch (RestClientException e) {
            log.error("SMK API failed: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch artworks from SMK API", e);
        }
    }

    private static SourceArtwork toSourceArtwork(SmkItem item) {
        return new SourceArtwork(
                imageId(item.imageIiifId()),
                first(item.titles(), SmkItem.Title::title),
                join(item.artist()),
                first(item.productionDate(), SmkItem.ProductionDate::period),
                join(item.techniques()),
                null,
                first(item.dimensions(), SmkItem.Dimension::notes),
                item.responsibleDepartment(),
                null,
                null,
                join(item.contentDescription())
        );
    }

    private static String imageId(String iiifId) {
        if (iiifId == null || !iiifId.startsWith(IIIF_URL_PREFIX)) {
            return null;
        }
        return iiifId.substring(IIIF_URL_PREFIX.length());
    }

    private static <T> String first(List<T> values, java.util.function.Function<T, String> field) {
        return values == null || values.isEmpty() ? null : field.apply(values.getFirst());
    }

    private static String join(List<String> values) {
        return values == null || values.isEmpty() ? null : String.join(", ", values);
    }
}
