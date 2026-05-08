package nl.vtek.names.game.controller;

import nl.vtek.names.game.dto.HintResponse;
import nl.vtek.names.game.service.HintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("api/v1/hints")
public class HintController {

    private final HintService hintService;

    public HintController(HintService hintService) {
        this.hintService = hintService;
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<HintResponse> getHintByGameId(@PathVariable Long gameId) {
        return hintService.getHintByGameId(gameId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
