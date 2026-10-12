package nl.vtek.names.game.exception;


public class UnknownArtSourceException extends BadRequestException {
    public UnknownArtSourceException(String key) {
        super("Unknown art source: " + key);
    }
}
