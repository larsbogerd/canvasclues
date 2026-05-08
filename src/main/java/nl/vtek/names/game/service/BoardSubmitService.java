package nl.vtek.names.game.service;

import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.SubmitRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardSubmitService {

    private final GameService gameService;
    private final CardService cardService;
    private final HintService hintService;
    private final ArtworkService artworkService;

    public BoardSubmitService(GameService gameService,
                              CardService cardService,
                              HintService hintService,
                              ArtworkService artworkService) {
        this.gameService = gameService;
        this.cardService = cardService;
        this.hintService = hintService;
        this.artworkService = artworkService;
    }

    @Transactional
    public void submit(Long gameId, SubmitRequest request) {
        cardService.updateCards(request.cardIds(), true);
        gameService.updateMaxScore(gameId, request.maxScore());
        hintService.createHint(gameId, request.hintContent());
        artworkService.recordArtworkUsage(gameId);
        gameService.markReady(gameId);
    }
}
