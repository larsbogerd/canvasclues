package nl.vtek.names.art.util;

public class IiifUrlBuilder {

    private IiifUrlBuilder() {
        /* This utility class should not be instantiated */
    }

    /**
     * Region {@code full} and an explicit size behave identically in IIIF Image API 2 and 3,
     * so one template serves both — as long as callers never ask for the size keyword
     * {@code full}/{@code max}, which is the only part that differs between the versions.
     */
    public static String template(String basePath, String imageId) {
        return "%s/%s/full/{size}/0/default.jpg".formatted(basePath, imageId);
    }
}
