package nl.vtek.names.game.exception;

import java.util.UUID;

/**
 * 400 - The card exists, but belongs to a different game than the session's.
 *
 * Thrown when an Operative submits a guess on a card that isn't part of
 * their session's board (e.g. stale cardId from a previous game, or a
 * forged request).
 */
public class CardNotInSessionGameException extends BadRequestException {
    public CardNotInSessionGameException(UUID cardId, Long gameId) {
        super("Card " + cardId + " does not belong to game " + gameId);
    }
}
