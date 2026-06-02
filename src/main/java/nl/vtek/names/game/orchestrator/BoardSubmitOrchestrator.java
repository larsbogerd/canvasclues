package nl.vtek.names.game.orchestrator;

import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.BoardSubmitRequest;
import nl.vtek.names.game.interfaces.GameMode;
import nl.vtek.names.game.service.CardService;
import nl.vtek.names.game.service.GameModeRegistry;
import nl.vtek.names.game.service.GameService;
import nl.vtek.names.game.service.HintService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BoardSubmitOrchestrator {

    private final GameService gameService;
    private final CardService cardService;
    private final HintService hintService;
    private final ArtworkService artworkService;
    private final GameModeRegistry gameModeRegistry;

    public BoardSubmitOrchestrator(GameService gameService,
                                   CardService cardService,
                                   HintService hintService,
                                   ArtworkService artworkService,
                                   GameModeRegistry gameModeRegistry) {
        this.gameService = gameService;
        this.cardService = cardService;
        this.hintService = hintService;
        this.artworkService = artworkService;
        this.gameModeRegistry = gameModeRegistry;
    }

    @Transactional
    public void submit(Long gameId, BoardSubmitRequest request) {
        cardService.updateCards(request.cardIds(), true);
        hintService.createHint(gameId, request.hintContent());
        artworkService.recordArtworkUsage(gameId);
        gameService.markReady(gameId);
        GameMode gameMode = gameModeRegistry.getMode(gameService.getModeName(gameId));
        gameMode.handleSpyScore(gameId, request.spyScore());
    }
}
