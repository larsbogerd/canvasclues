package nl.vtek.names.game.exception;

import java.util.UUID;

/**
 * 409 - The session has already been finished and cannot accept new guesses.
 *
 * Thrown when an Operative tries to submit a guess (or otherwise mutate)
 * a session whose state is already {@code FINISHED}. Prevents score
 * tampering after the round is over.
 */
public class SessionAlreadyFinishedException extends ConflictException {
    public SessionAlreadyFinishedException(UUID sessionId) {
        super("Session " + sessionId + " is already finished");
    }
}
