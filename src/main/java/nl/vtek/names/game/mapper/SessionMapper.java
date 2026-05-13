package nl.vtek.names.game.mapper;

import nl.vtek.names.game.dto.SessionResponse;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Session;
import nl.vtek.names.game.model.Hint;

import java.util.List;

public class SessionMapper {
    private SessionMapper() {
        /* This utility class should not be instantiated */
    }

    public static SessionResponse toSessionResponse(Session session, List<Card> cards, Hint hint) {
        int spymasterPickCount = (int) cards.stream().filter(Card::isSpymasterPick).count();
        return new SessionResponse(
                session.getId(),
                CardMapper.toCardResponse(cards),
                hint == null ? null : HintMapper.toHintResponse(hint),
                spymasterPickCount
        );
    }
}
