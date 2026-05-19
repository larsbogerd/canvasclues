package nl.vtek.names.game.exception;

import java.util.UUID;

public class ArtworkNotFoundException extends ResourceNotFoundException {
    public ArtworkNotFoundException(UUID artworkId) {
        super("Artwork", artworkId);
    }
}
