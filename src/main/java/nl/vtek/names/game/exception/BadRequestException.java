package nl.vtek.names.game.exception;

/**
 * This is for all 400 errors.
 *
 * Use when the client sends well-formed data that doesn't make sense
 * in context (e.g. a cardId that belongs to a different game than
 * the session does).
 */
public abstract class BadRequestException extends RuntimeException {
    protected BadRequestException(String message) {
        super(message);
    }
}
