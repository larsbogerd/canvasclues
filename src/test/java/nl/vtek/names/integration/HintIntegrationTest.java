package nl.vtek.names.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.GameState;
import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.HintRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class HintIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HintRepo hintRepo;

    @Autowired
    private GameRepository gameRepository;

    @Test
    void submitHint_savesToDatabase() throws Exception {
        Game game = new Game();
        game.setState(GameState.CREATING);
        game = gameRepository.save(game);

        String json = """
                { "cardIds": [], "maxScore": 0, "hintContent": "testhint" }
                """;

        mockMvc.perform(post("/api/v1/game/%d/submit".formatted(game.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        Optional<Hint> saved = hintRepo.findByGame_Id(game.getId());
        assertThat(saved).isPresent();
        assertThat(saved.get().getContent()).isEqualTo("testhint");
    }
}
