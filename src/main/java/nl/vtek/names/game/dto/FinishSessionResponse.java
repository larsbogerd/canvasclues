package nl.vtek.names.game.dto;

public record FinishSessionResponse(
        int score,
        int wrongGuesses,
        int assassinGuesses
) {
}
