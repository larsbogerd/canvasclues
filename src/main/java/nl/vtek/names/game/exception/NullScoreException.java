package nl.vtek.names.game.exception;


public class NullScoreException
        extends BadRequestException {
    public NullScoreException() {
        super("Spymaster score can't be null");
    }
}
