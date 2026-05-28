package nl.vtek.names.game.controller;

import nl.vtek.names.game.dto.FinishSessionResponse;
import nl.vtek.names.game.dto.GuessSubmitResponse;
import nl.vtek.names.game.dto.SessionResponse;
import nl.vtek.names.game.orchestrator.GuessSubmitOrchestrator;
import nl.vtek.names.game.service.SessionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/session")
public class SessionController {

    private final SessionService sessionService;
    private final GuessSubmitOrchestrator guessSubmitOrchestrator;

    public SessionController(SessionService sessionService,
                             GuessSubmitOrchestrator guessSubmitOrchestrator) {
        this.sessionService = sessionService;
        this.guessSubmitOrchestrator = guessSubmitOrchestrator;
    }

    @PostMapping("/{gameId}/start")
    public SessionResponse play(@PathVariable Long gameId) {
        return sessionService.start(gameId);
    }

    @PostMapping("/{sessionId}/guess/{cardId}")
    public GuessSubmitResponse guess(@PathVariable UUID sessionId, @PathVariable UUID cardId) {
        return guessSubmitOrchestrator.submit(sessionId, cardId);
    }

    @PostMapping("/{sessionId}/finish")
    public FinishSessionResponse finish(@PathVariable UUID sessionId) {
        return sessionService.finish(sessionId);
    }
}
