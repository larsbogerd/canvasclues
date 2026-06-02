package nl.vtek.names.game.orchestrator;

import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.GuessSubmitResponse;
import nl.vtek.names.game.interfaces.GameMode;
import nl.vtek.names.game.mapper.GuessMapper;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Session;
import nl.vtek.names.game.service.CardService;
import nl.vtek.names.game.service.GameModeRegistry;
import nl.vtek.names.game.service.SessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GuessSubmitOrchestrator {

    private final SessionService sessionService;
    private final CardService cardService;
    private final ArtworkService artworkService;
    private final GameModeRegistry gameModeRegistry;


    public GuessSubmitOrchestrator(SessionService sessionService,
                                   CardService cardService,
                                   ArtworkService artworkService,
                                   GameModeRegistry gameModeRegistry) {
        this.sessionService = sessionService;
        this.cardService = cardService;
        this.artworkService = artworkService;
        this.gameModeRegistry = gameModeRegistry;
    }

    @Transactional
    public GuessSubmitResponse submit(UUID sessionId, UUID cardId) {
        Session session = sessionService.getActiveSession(sessionId);
        Card card = cardService.getCardInGame(cardId, session.getGame().getId());
        GameMode gameMode = gameModeRegistry.getMode(session.getGame().getGameModeName());
        gameMode.recordGuess(session, card);
        artworkService.recordGuessUsage(card.getArtwork(), card.isSpymasterPick());
        sessionService.finishIfAssassinated(session);
        return GuessMapper.toGuessSubmitResponse(session, card);
    }
}
