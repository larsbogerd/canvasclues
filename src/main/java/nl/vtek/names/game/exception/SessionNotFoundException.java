package nl.vtek.names.game.exception;

import java.util.UUID;

public class SessionNotFoundException extends ResourceNotFoundException {
    public SessionNotFoundException(UUID sessionId) {
        super("Session", sessionId);
    }
}
