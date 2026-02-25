package nl.vtek.names.game.controller;

import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.HintRepo;
import nl.vtek.names.game.service.HintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping
    public ResponseEntity<Hint> getNewestHint() {
        return hintService.getNewestHint()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public void createHint(@RequestBody Hint hint) {
        hintService.createHint(hint);

    }

}
