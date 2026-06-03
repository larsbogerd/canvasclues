package nl.vtek.names.game.dto;

public record GuessSubmitResponse(
        boolean correct,
        int score,
        int comboStreak,
        int wrongGuesses,
        int assassinGuesses,
        String cardType
) {
}
