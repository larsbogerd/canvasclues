package nl.vtek.names.game.controller;

import nl.vtek.names.game.dto.UpdateCardsRequest;
import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.service.GameService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public List<CardResponse> startGame() {
        return gameService.startGame();
    }

    @GetMapping("/{gameId}")
    public List<CardResponse> getGame(@PathVariable int gameId) {
        return gameService.getGame(gameId);
    }

    @PatchMapping("/updatecards")
    public void updateCards(@RequestBody UpdateCardsRequest request) {
        gameService.updateCards(request.getCardIds(), request.getSpymasterPick());
    }

    @PostMapping("/checkcards")
    public Map<UUID, Boolean> checkCards(@RequestBody List<UUID> cardIds) {
        return gameService.checkCards(cardIds);
    }
}
