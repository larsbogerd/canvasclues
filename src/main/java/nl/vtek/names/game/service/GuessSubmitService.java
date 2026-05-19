package nl.vtek.names.game.service;

import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.GuessSubmitResponse;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GuessSubmitService {

    private final SessionService sessionService;
    private final CardService cardService;
    private final ArtworkService artworkService;

    public GuessSubmitService(SessionService sessionService,
                              CardService cardService,
                              ArtworkService artworkService) {
        this.sessionService = sessionService;
        this.cardService = cardService;
        this.artworkService = artworkService;
    }

    @Transactional
    public GuessSubmitResponse submit(UUID sessionId, UUID cardId) {
        Session session = sessionService.getActiveSession(sessionId);
        Card card = cardService.getCardInGame(cardId, session.getGame().getId());

        boolean correct = card.isSpymasterPick();
        
        sessionService.recordGuess(session, correct);
        artworkService.recordGuessUsage(card.getArtwork(), correct);

        return new GuessSubmitResponse(correct, session.getScore());
    }
}
