package nl.vtek.names.repository;

import static org.assertj.core.api.Assertions.assertThat;

import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.HintRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Optional;

@SpringBootTest
class HintRepoTest {

    @Autowired
    private HintRepo hintRepo;

    @Test
    void save_persistsHintAndCanFindById() {
        Hint hint = new Hint("testhint", 111111);
        hint = hintRepo.save(hint);

        boolean exists = hintRepo.existsById(hint.getHintId());
        assertThat(exists).isTrue();
    }

    @Test
    void findByGameId_returnsCorrectHint() {
        Hint hint = new Hint("testhint", 222222);
        hintRepo.save(hint);

        Optional<Hint> result = hintRepo.findByGameId(222222);
        assertThat(result).isPresent();
        assertThat(result.get().getHintContent()).isEqualTo("testhint");
    }

    @Test
    void findByGameId_returnsEmptyWhenNotFound() {
        Optional<Hint> result = hintRepo.findByGameId(999);
        assertThat(result).isEmpty();
    }
}
