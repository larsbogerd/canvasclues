package nl.vtek.names.game.controller;

import nl.vtek.names.game.dto.SessionFinishRequest;
import nl.vtek.names.game.dto.SessionResponse;
import nl.vtek.names.game.service.SessionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/{gameId}/start")
    public SessionResponse play(@PathVariable Long gameId) {
        return sessionService.start(gameId);
    }

    @PostMapping("/{sessionId}/finish")
    public void finish(@PathVariable UUID sessionId, @RequestBody SessionFinishRequest request) {
        sessionService.finish(sessionId, request.score());
    }
}
