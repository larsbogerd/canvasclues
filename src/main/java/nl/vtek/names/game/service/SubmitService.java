package nl.vtek.names.game.service;

import nl.vtek.names.game.dto.SubmitRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubmitService {

    private final GameService gameService;
    private final HintService hintService;

    public SubmitService(GameService gameService, HintService hintService) {
        this.gameService = gameService;
        this.hintService = hintService;
    }

    @Transactional
    public void submit(int gameId, SubmitRequest request) {
        gameService.updateCards(request.cardIds(), true);
        gameService.updateMaxScore(gameId, request.maxScore());
        hintService.createHint(gameId, request.hintContent());
        gameService.submitMetadata(gameId);
    }
}
