package nl.vtek.names.art.source;

import java.util.List;

public interface ArtSource {

    String key();

    /**
     * Returns up to {@code size} public-domain artworks that have an image, picked at random
     * so repeated calls do not return the same board.
     */
    List<SourceArtwork> fetchArtworks(int size);

    /** An image URL with a {@code {size}} placeholder for the caller to fill in. */
    String imageUrlTemplate(String imageId);
}
