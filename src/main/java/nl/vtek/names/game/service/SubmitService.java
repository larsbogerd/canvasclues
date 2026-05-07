package nl.vtek.names.game.service;

import nl.vtek.names.game.dto.SubmitRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubmitService {

    private final GameService gameService;
    private final HintService hintService;
    private final CardService cardService;

    public SubmitService(GameService gameService, HintService hintService,  CardService cardService) {
        this.gameService = gameService;
        this.hintService = hintService;
        this.cardService = cardService;
    }

    @Transactional
    public void submit(int gameId, SubmitRequest request) {
        cardService.updateCards(request.cardIds(), true);
        gameService.updateMaxScore(gameId, request.maxScore());
        hintService.createHint(gameId, request.hintContent());
        gameService.submitMetadata(gameId);
    }
}
