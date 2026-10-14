package nl.vtek.names.integration;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.repository.ArtworkRepository;
import nl.vtek.names.art.source.ArtSource;
import nl.vtek.names.art.source.SourceArtwork;
import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.dto.GuessSubmitResponse;
import nl.vtek.names.game.dto.SessionResponse;
import nl.vtek.names.game.model.CardType;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.GameState;
import nl.vtek.names.game.repository.CardRepository;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.HintRepository;
import nl.vtek.names.game.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GameFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private HintRepository hintRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void emptyTheDatabase() {
        sessionRepository.deleteAll();
        hintRepository.deleteAll();
        cardRepository.deleteAll();
        gameRepository.deleteAll();
        artworkRepository.deleteAll();
    }

    @Test
    void wholeGame_fromBoardToFinishedSession() throws Exception {
        List<CardResponse> board = startGame("makkelijk", "fake");
        Long gameId = board.getFirst().gameId();

        assertThat(board).hasSize(16).allMatch(card -> card.gameId().equals(gameId));
        assertThat(board).filteredOn(card -> card.type() == CardType.ASSASSIN).hasSize(4);
        assertThat(board).allSatisfy(card -> {
            assertThat(card.source()).isEqualTo("fake");
            assertThat(card.imageUrlTemplate()).startsWith("/img/fake/").contains("{size}");
            assertThat(card.altText()).startsWith(card.title() + " — " + card.artistDisplay());
        });

        List<CardResponse> playable = board.stream().filter(card -> card.type() == CardType.PLAYABLE).toList();
        List<CardResponse> assassins = board.stream().filter(card -> card.type() == CardType.ASSASSIN).toList();
        List<UUID> picks = playable.stream().limit(3).map(CardResponse::id).toList();

        submitBoard(gameId, picks, 3, "Zonnebloem").andExpect(status().isOk());

        assertThat(gameRepository.findById(gameId)).get().satisfies(game -> {
            assertThat(game.getState()).isEqualTo(GameState.READY);
            assertThat(game.getSpyScore()).isEqualTo(60);
            assertThat(game.getSource()).isEqualTo("fake");
        });
        assertThat(cardRepository.findByGame_Id(gameId))
                .allMatch(card -> card.isSpymasterPick() == picks.contains(card.getId()));
        assertThat(hintRepository.findByGame_Id(gameId)).get()
                .extracting(hint -> hint.getContent()).isEqualTo("zonnebloem");

        List<GameResponse> games = read(mockMvc.perform(get("/api/v1/game/list")), new TypeReference<>() {});
        assertThat(games).anyMatch(game -> game.gameId().equals(gameId)
                && "zonnebloem".equals(game.hint()) && "fake".equals(game.source()));

        SessionResponse session = read(mockMvc.perform(post("/api/v1/session/{id}/start", gameId))
                        .andExpect(jsonPath("$.cards[*].type").doesNotExist()),
                new TypeReference<>() {});
        assertThat(session.cards()).hasSize(16);
        assertThat(session.spymasterPickCount()).isEqualTo(3);
        assertThat(session.hint().content()).isEqualTo("zonnebloem");

        GuessSubmitResponse first = guess(session.sessionId(), picks.get(0));
        GuessSubmitResponse second = guess(session.sessionId(), picks.get(1));
        assertThat(first.correct()).isTrue();
        assertThat(first.score()).isEqualTo(20);
        assertThat(second.score()).isEqualTo(50);
        assertThat(second.comboStreak()).isEqualTo(2);

        guess(session.sessionId(), assassins.get(0).id());
        GuessSubmitResponse fatal = guess(session.sessionId(), assassins.get(1).id());
        assertThat(fatal.assassinGuesses()).isEqualTo(2);
        assertThat(fatal.comboStreak()).isZero();

        mockMvc.perform(post("/api/v1/session/{s}/guess/{c}", session.sessionId(), picks.get(2)))
                .andExpect(status().isConflict());
        mockMvc.perform(post("/api/v1/session/{s}/finish", session.sessionId()))
                .andExpect(status().isConflict());

        Artwork pickedArtwork = artworkRepository.findById(playable.getFirst().artworkId()).orElseThrow();
        assertThat(pickedArtwork.getTimesLoaded()).isEqualTo(1);
        assertThat(pickedArtwork.getTimesSpymasterPick()).isEqualTo(1);
        assertThat(pickedArtwork.getTimesCorrectGuess()).isEqualTo(1);
    }

    @Test
    void startGame_badInput_isA400() throws Exception {
        mockMvc.perform(post("/api/v1/game/start").param("gameMode", "extreem"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unknown gamemode"));
        mockMvc.perform(post("/api/v1/game/start"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(post("/api/v1/game/start").param("gameMode", "makkelijk").param("source", "louvre"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unknown art source: louvre"));
    }

    @Test
    void submitBoard_pickingAnAssassin_isRejectedAndChangesNothing() throws Exception {
        List<CardResponse> board = startGame("makkelijk", "fake");
        Long gameId = board.getFirst().gameId();
        List<UUID> picks = board.stream()
                .sorted((a, b) -> a.type().compareTo(b.type()))
                .map(CardResponse::id)
                .toList()
                .subList(10, 14);

        submitBoard(gameId, picks, 4, "zon").andExpect(status().isBadRequest());

        assertThat(gameRepository.findById(gameId)).get()
                .extracting(Game::getState).isEqualTo(GameState.CREATING);
        assertThat(cardRepository.findByGame_Id(gameId)).noneMatch(card -> card.isSpymasterPick());
        assertThat(hintRepository.findByGame_Id(gameId)).isEmpty();
    }

    @Test
    void submitBoard_invalidHint_isA400() throws Exception {
        Long gameId = startGame("makkelijk", "fake").getFirst().gameId();

        submitBoard(gameId, List.of(), 2, "twee woorden")
                .andExpect(status().isBadRequest());
    }

    @Test
    void session_onAnUnfinishedBoard_isAConflict() throws Exception {
        Long gameId = startGame("makkelijk", "fake").getFirst().gameId();

        mockMvc.perform(post("/api/v1/session/{id}/start", gameId)).andExpect(status().isConflict());
        mockMvc.perform(post("/api/v1/session/{id}/start", 987654L)).andExpect(status().isNotFound());
    }

    @Test
    void guess_cardFromAnotherGame_isA400() throws Exception {
        List<CardResponse> mine = startGame("makkelijk", "fake");
        List<CardResponse> other = startGame("makkelijk", "fake");
        Long gameId = mine.getFirst().gameId();
        List<UUID> picks = mine.stream().filter(card -> card.type() == CardType.PLAYABLE)
                .limit(2).map(CardResponse::id).toList();
        submitBoard(gameId, picks, 2, "zon").andExpect(status().isOk());
        SessionResponse session = read(mockMvc.perform(post("/api/v1/session/{id}/start", gameId)),
                new TypeReference<>() {});

        mockMvc.perform(post("/api/v1/session/{s}/guess/{c}", session.sessionId(), other.getFirst().id()))
                .andExpect(status().isBadRequest());
    }

    private List<CardResponse> startGame(String mode, String source) throws Exception {
        return read(mockMvc.perform(post("/api/v1/game/start").param("gameMode", mode).param("source", source)),
                new TypeReference<>() {});
    }

    private ResultActions submitBoard(Long gameId, List<UUID> picks, int spyScore, String hint) throws Exception {
        String body = objectMapper.writeValueAsString(Map.of(
                "cardIds", picks, "spyScore", spyScore, "hintContent", hint));
        return mockMvc.perform(post("/api/v1/game/{id}/submit", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
    }

    private GuessSubmitResponse guess(UUID sessionId, UUID cardId) throws Exception {
        return read(mockMvc.perform(post("/api/v1/session/{s}/guess/{c}", sessionId, cardId)),
                new TypeReference<>() {});
    }

    private <T> T read(ResultActions result, TypeReference<T> type) throws Exception {
        String json = result.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(json, type);
    }

    @TestConfiguration
    static class FakeSources {

        @Bean
        FakeSource fakeSource() {
            return new FakeSource("fake");
        }
    }

    static class FakeSource implements ArtSource {

        private final String key;
        private final AtomicInteger calls = new AtomicInteger();

        FakeSource(String key) {
            this.key = key;
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public List<SourceArtwork> fetchArtworks(int size) {
            int call = calls.incrementAndGet();
            return IntStream.range(0, size)
                    .mapToObj(i -> new SourceArtwork("%s-%d-%d".formatted(key, call, i), "Painting " + i,
                            "Painter " + i, "1889", "Oil on canvas", null, null, null, null, null, null))
                    .toList();
        }
    }
}
