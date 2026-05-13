package nl.vtek.names.game.controller;

import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.dto.SubmitRequest;
import nl.vtek.names.game.service.BoardSubmitService;
import nl.vtek.names.game.service.CardService;
import nl.vtek.names.game.service.GameService;
import nl.vtek.names.game.service.StartGameService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/game")
public class GameController {

    private final GameService gameService;
    private final CardService cardService;
    private final StartGameService startGameService;
    private final BoardSubmitService boardSubmitService;

    public GameController(GameService gameService,
                          CardService cardService,
                          StartGameService startGameService,
                          BoardSubmitService boardSubmitService) {
        this.gameService = gameService;
        this.cardService = cardService;
        this.startGameService = startGameService;
        this.boardSubmitService = boardSubmitService;
    }

    @PostMapping("/start")
    public List<CardResponse> startGame() {
        return startGameService.startGame();
    }

    @GetMapping("/list")
    public List<GameResponse> getGameList() {
        return gameService.getGameList();
    }

    @PostMapping("/{gameId}/submit")
    public void submit(@PathVariable Long gameId, @RequestBody SubmitRequest request) {
        boardSubmitService.submit(gameId, request);
    }

    @PostMapping("/checkcards")
    public Map<UUID, Boolean> checkCards(@RequestBody List<UUID> cardIds) {
        return cardService.checkCards(cardIds);
    }
}
