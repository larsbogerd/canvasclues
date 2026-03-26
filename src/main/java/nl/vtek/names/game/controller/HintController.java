package nl.vtek.names.game.controller;

import nl.vtek.names.game.dto.HintRequest;
import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.HintRepo;
import nl.vtek.names.game.service.HintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin
@RequestMapping("api/v1/hints")
public class HintController {

    private final HintService hintService;
    private final HintRepo hintRepo;


    public HintController(HintService hintService, HintRepo hintRepo) {
        this.hintService = hintService;
        this.hintRepo = hintRepo;
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Hint> getHintByGameId(@PathVariable int gameId) {
        return hintService.getHintByGameId(gameId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public void createHint(@RequestBody HintRequest hintRequest) {
        hintService.createHint(hintRequest);

    }

}
