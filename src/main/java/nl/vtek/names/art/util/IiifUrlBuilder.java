package nl.vtek.names.art.util;

import java.util.UUID;

public class IiifUrlBuilder {

    private static final String IIIF_ARTIC_BASE_URL = "https://www.artic.edu/iiif/2";

    private IiifUrlBuilder() {
        /* This utility class should not be instantiated */
    }

    public static String forArtwork(UUID artworkId) {
        return "%s/%s/full/!500,500/0/default.jpg".formatted(IIIF_ARTIC_BASE_URL, artworkId);
    }
}
