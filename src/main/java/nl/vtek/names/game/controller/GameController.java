package nl.vtek.names.game.controller;

import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.dto.SubmitRequest;
import nl.vtek.names.game.service.CardService;
import nl.vtek.names.game.service.GameService;
import nl.vtek.names.game.service.SubmitService;
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
    private final SubmitService submitService;
    private final CardService cardService;

    public GameController(GameService gameService, SubmitService submitService, CardService cardService) {
        this.gameService = gameService;
        this.submitService = submitService;
        this.cardService = cardService;
    }

    @PostMapping("/start")
    public List<CardResponse> startGame() {
        return gameService.startGame();
    }

    @GetMapping("/{gameId}")
    public List<CardResponse> getGame(@PathVariable int gameId) {
        return gameService.getGame(gameId);
    }

    @GetMapping("/list")
    public List<GameResponse> getGameList() {
        return gameService.getGameList();
    }

    @PostMapping("/{gameId}/submit")
    public void submit(@PathVariable int gameId, @RequestBody SubmitRequest request) {
        submitService.submit(gameId, request);
    }

    @PostMapping("/checkcards")
    public Map<UUID, Boolean> checkCards(@RequestBody List<UUID> cardIds) {
        return cardService.checkCards(cardIds);
    }
}
