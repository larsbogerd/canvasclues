package nl.vtek.names.game.controller;

import nl.vtek.names.game.dto.CardUpdateRequest;
import nl.vtek.names.game.model.GameCard;
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

@RestController
@CrossOrigin
@RequestMapping("/api/v1/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public List<GameCard> startGame() {
        return gameService.startGame();
    }

    @GetMapping("/{gameId}")
    public List<GameCard> getGame(@PathVariable int gameId) {
        return gameService.getGame(gameId);
    }

    @PatchMapping("/cards")
    public List<GameCard> updateCards(@RequestBody CardUpdateRequest request) {
        return gameService.updateCards(request.getCardIds(), request.getSpymasterPick(), request.getRevealed());
    }
}
