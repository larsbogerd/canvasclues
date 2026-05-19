package nl.vtek.names.game.exception;

import java.util.UUID;

public class HintNotFoundException extends ResourceNotFoundException {
    public HintNotFoundException(UUID hintId) {
        super("Hint", hintId);
    }
}
