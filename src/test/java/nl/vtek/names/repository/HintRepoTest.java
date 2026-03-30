package nl.vtek.names.repository;

import static org.assertj.core.api.Assertions.assertThat;

import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.GameState;
import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.HintRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class HintRepoTest {

    @Autowired
    private HintRepo hintRepo;

    @Autowired
    private GameRepository gameRepository;

    private Game createAndSaveGame() {
        Game game = new Game();
        game.setState(GameState.CREATING);
        return gameRepository.save(game);
    }

    @Test
    void save_persistsHintAndCanFindById() {
        Game game = createAndSaveGame();
        Hint hint = new Hint("testhint", game);
        hint = hintRepo.save(hint);

        boolean exists = hintRepo.existsById(hint.getId());
        assertThat(exists).isTrue();
    }

    @Test
    void findByGameId_returnsCorrectHint() {
        Game game = createAndSaveGame();
        Hint hint = new Hint("testhint", game);
        hintRepo.save(hint);

        Optional<Hint> result = hintRepo.findByGame_Id(game.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getContent()).isEqualTo("testhint");
    }

    @Test
    void findByGameId_returnsEmptyWhenNotFound() {
        Optional<Hint> result = hintRepo.findByGame_Id(999999);
        assertThat(result).isEmpty();
    }
}
