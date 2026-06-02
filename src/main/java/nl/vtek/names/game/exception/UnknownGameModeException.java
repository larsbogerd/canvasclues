package nl.vtek.names.game.exception;


public class UnknownGameModeException extends IllegalArgumentException {
    public UnknownGameModeException() {
        super("Unknown gamemode");
    }
}
