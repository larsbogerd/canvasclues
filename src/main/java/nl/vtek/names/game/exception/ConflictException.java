package nl.vtek.names.game.exception;

/**
 * This is for all 409 errors.
 *
 * Use when the request was understood fine but conflicts with the
 * current state of the resource (e.g. trying to submit a guess to
 * a session that's already finished).
 */
public abstract class ConflictException extends RuntimeException {
    protected ConflictException(String message) {
        super(message);
    }
}
