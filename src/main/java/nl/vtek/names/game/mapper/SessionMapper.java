package nl.vtek.names.game.mapper;

import nl.vtek.names.game.dto.FinishSessionResponse;
import nl.vtek.names.game.dto.SessionResponse;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Session;
import nl.vtek.names.game.model.Hint;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionMapper {

    private final CardMapper cardMapper;

    public SessionMapper(CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    public SessionResponse toSessionResponse(Session session, List<Card> cards, Hint hint) {
        int spymasterPickCount = (int) cards.stream().filter(Card::isSpymasterPick).count();
        return new SessionResponse(
                session.getId(),
                cardMapper.toCardResponse(cards),
                hint == null ? null : HintMapper.toHintResponse(hint),
                spymasterPickCount
        );
    }

    public FinishSessionResponse toFinishSessionResponse(Session session) {
        return new FinishSessionResponse(
                session.getScore(),
                session.getWrongGuesses(),
                session.getAssassinGuesses()
        );
    }
}
