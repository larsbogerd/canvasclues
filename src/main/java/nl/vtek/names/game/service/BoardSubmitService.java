package nl.vtek.names.game.service;

import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.BoardSubmitRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BoardSubmitService {

    private final GameService gameService;
    private final CardService cardService;
    private final HintService hintService;
    private final ArtworkService artworkService;
    private final ScoreService scoreService;

    public BoardSubmitService(GameService gameService,
                              CardService cardService,
                              HintService hintService,
                              ArtworkService artworkService,
                              ScoreService scoreService) {
        this.gameService = gameService;
        this.cardService = cardService;
        this.hintService = hintService;
        this.artworkService = artworkService;
        this.scoreService = scoreService;
    }

    @Transactional
    public void submit(Long gameId, BoardSubmitRequest request) {
        cardService.updateCards(request.cardIds(), true);
        hintService.createHint(gameId, request.hintContent());
        artworkService.recordArtworkUsage(gameId);
        gameService.markReady(gameId);
        scoreService.handleSpyScore(gameId, request.spyScore());
    }
}
