package nl.vtek.names.game.exception;

import java.util.UUID;

public class CardNotFoundException extends ResourceNotFoundException {
    public CardNotFoundException(UUID cardId) {
        super("Card", cardId);
    }
}
