package nl.vtek.names.game.controller;

import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.dto.BoardSubmitRequest;
import nl.vtek.names.game.orchestrator.BoardSubmitOrchestrator;
import nl.vtek.names.game.service.GameService;
import nl.vtek.names.game.orchestrator.StartGameOrchestrator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final StartGameOrchestrator startGameOrchestrator;
    private final BoardSubmitOrchestrator boardSubmitOrchestrator;

    public GameController(GameService gameService,
                          StartGameOrchestrator startGameOrchestrator,
                          BoardSubmitOrchestrator boardSubmitOrchestrator) {
        this.gameService = gameService;
        this.startGameOrchestrator = startGameOrchestrator;
        this.boardSubmitOrchestrator = boardSubmitOrchestrator;
    }

    @PostMapping("/start")
    public List<CardResponse> startGame() {
        return startGameOrchestrator.startGame();
    }

    @GetMapping("/list")
    public List<GameResponse> getGameList() {
        return gameService.getGameList();
    }

    @PostMapping("/{gameId}/submit")
    public void submit(@PathVariable Long gameId, @RequestBody BoardSubmitRequest request) {
        boardSubmitOrchestrator.submit(gameId, request);
    }
}
