package nl.vtek.names.art.source;

import nl.vtek.names.art.util.IiifUrlBuilder;

import java.util.List;

public interface ArtSource {

    String key();

    /**
     * Returns up to {@code size} public-domain artworks that have an image, picked at random
     * so repeated calls do not return the same board.
     */
    List<SourceArtwork> fetchArtworks(int size);

    /**
     * An image URL with a {@code {size}} placeholder for the caller to fill in. Defaults to the
     * IIIF shape; a source that serves fixed-size derivatives instead overrides this.
     */
    default String imageUrlTemplate(String imageId) {
        return IiifUrlBuilder.template("/img/" + key(), imageId);
    }

    /**
     * Requests the board-size derivative once before the artwork enters the pool, for sources
     * that render derivatives on demand. Returns false when the image can't be served, which
     * keeps the artwork out of the pool.
     */
    default boolean warmImage(String imageId) {
        return true;
    }

    /** How many artworks one pool top-up asks the source for. */
    default int poolBatchSize() {
        return 100;
    }

    /**
     * Boards are drawn from the pool only once it holds this many artworks; below that they
     * are fetched live. Drawing from a pool barely bigger than a board repeats the same board.
     */
    default int minPoolSize() {
        return 64;
    }
}
