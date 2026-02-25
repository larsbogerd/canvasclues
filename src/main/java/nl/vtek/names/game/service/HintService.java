package nl.vtek.names.game.service;

import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.HintRepo;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Optional;

@Service
public class HintService {

    private final HintRepo hintRepo;


    public HintService(HintRepo hintRepo) {
        this.hintRepo = hintRepo;
    }

    public void createHint(Hint hint) {
        validateHint(hint);
        hintRepo.save(hint);
    }


    public Optional<Hint> getNewestHint() {
        return hintRepo.findTopByOrderByIdDesc();
    }


    private void validateHint(Hint hint) {
        if (hint == null || hint.getHintContent().isBlank()) {
            throw new InputMismatchException("Hint mag niet leeg zijn");
        }

        if (!hint.getHintContent().matches("[a-zA-Z]+")) {
            throw new InputMismatchException("Hint moet één woord zijn en mag geen cijfers bevatten");
        }
    }


}
