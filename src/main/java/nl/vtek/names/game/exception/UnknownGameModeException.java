package nl.vtek.names.game.exception;


public class UnknownGameModeException extends BadRequestException {
    public UnknownGameModeException() {
        super("Unknown gamemode");
    }
}
