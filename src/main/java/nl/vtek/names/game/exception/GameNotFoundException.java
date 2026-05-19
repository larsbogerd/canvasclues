package nl.vtek.names.game.exception;

public class GameNotFoundException extends ResourceNotFoundException {
    public GameNotFoundException(Long gameId) {
        super("Game", gameId);
    }
}
