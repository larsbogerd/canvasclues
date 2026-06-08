package nl.vtek.names.game.orchestrator;

import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.GuessSubmitResponse;
import nl.vtek.names.game.mapper.GuessMapper;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Session;
import nl.vtek.names.game.service.CardService;
import nl.vtek.names.game.service.GameModeRegistry;
import nl.vtek.names.game.service.ScoreService;
import nl.vtek.names.game.service.SessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GuessSubmitOrchestrator {

    private final SessionService sessionService;
    private final CardService cardService;
    private final ScoreService scoreService;
    private final ArtworkService artworkService;


    public GuessSubmitOrchestrator(SessionService sessionService,
                                   CardService cardService, ScoreService scoreService,
                                   ArtworkService artworkService) {
        this.sessionService = sessionService;
        this.cardService = cardService;
        this.scoreService = scoreService;
        this.artworkService = artworkService;
    }

    @Transactional
    public GuessSubmitResponse submit(UUID sessionId, UUID cardId) {
        Session session = sessionService.getActiveSession(sessionId);
        Card card = cardService.getCardInGame(cardId, session.getGame().getId());
        scoreService.recordGuess(session, card);
        artworkService.recordGuessUsage(card.getArtwork(), card.isSpymasterPick());
        sessionService.finishIfAssassinated(session);
        return GuessMapper.toGuessSubmitResponse(session, card);
    }
}
