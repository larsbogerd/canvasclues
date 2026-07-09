package nl.vtek.names.game.exception;

public class GameNotPlayableException extends ConflictException {
    public GameNotPlayableException(Long gameId) {
        super("Game " + gameId + " is not playable");
    }
}
