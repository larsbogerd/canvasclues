package nl.vtek.names.game.mapper;

import nl.vtek.names.game.dto.GuessSubmitResponse;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Session;

public class GuessMapper {
    private GuessMapper() {
        /* This utility class should not be instantiated */
    }

    public static GuessSubmitResponse toGuessSubmitResponse(Session session, Card card) {
        return new GuessSubmitResponse(
                card.isSpymasterPick(),
                session.getScore(),
                session.getComboStreak(),
                session.getWrongGuesses(),
                session.getAssassinGuesses(),
                card.getType().name()
        );
    }
}
