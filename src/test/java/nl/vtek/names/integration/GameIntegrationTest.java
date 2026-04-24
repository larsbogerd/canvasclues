package nl.vtek.names.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.service.GameService;
import nl.vtek.names.game.repository.CardRepository;
import nl.vtek.names.game.model.Card;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class GameIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void startGame_returns16CardsFromDb() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/game/start"))
                .andExpect(status().isOk())
                .andReturn();

        List<CardResponse> returnedCards = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        assertThat(returnedCards).hasSize(GameService.BOARD_SIZE);

        int gameId = returnedCards.getFirst().getGameId();
        assertThat(gameId).isNotZero();
        assertThat(returnedCards).allMatch(card -> card.getGameId() == gameId);

        assertThat(returnedCards).allSatisfy(card -> {
            assertThat(card.getImageUrl()).isNotEmpty();
            assertThat(card.getTitle()).isNotBlank();
            assertThat(card.getArtistDisplay()).isNotBlank();
            assertThat(card.getDateDisplay()).isNotBlank();
            assertThat(card.getAltText()).isEqualTo("%s — %s, %s".formatted(
                    card.getTitle(),
                    card.getArtistDisplay(),
                    card.getDateDisplay()
            ));
        });

        List<Card> dbCards = cardRepository.findByGame_Id(gameId);
        assertThat(dbCards).hasSize(GameService.BOARD_SIZE);
    }

    @Test
    void getGame_returnsCreatedCards() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/game/start"))
                .andExpect(status().isOk())
                .andReturn();

        List<CardResponse> returnedCards = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        int gameId = returnedCards.getFirst().getGameId();

        MvcResult getResult = mockMvc.perform(get("/api/v1/game/" + gameId))
                .andExpect(status().isOk())
                .andReturn();

        List<CardResponse> fetchedCards = objectMapper.readValue(
                getResult.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        assertThat(fetchedCards).hasSize(GameService.BOARD_SIZE);
        assertThat(fetchedCards).allMatch(card -> card.getGameId() == gameId);
        assertThat(fetchedCards).allSatisfy(card -> {
            assertThat(card.getTitle()).isNotBlank();
            assertThat(card.getArtistDisplay()).isNotBlank();
            assertThat(card.getDateDisplay()).isNotBlank();
            assertThat(card.getAltText()).isEqualTo("%s — %s, %s".formatted(
                    card.getTitle(),
                    card.getArtistDisplay(),
                    card.getDateDisplay()
            ));
        });

        List<UUID> createdIds = returnedCards.stream().map(CardResponse::getId).toList();
        List<UUID> fetchedIds = fetchedCards.stream().map(CardResponse::getId).toList();
        assertThat(fetchedIds).containsExactlyInAnyOrderElementsOf(createdIds);
    }

    @Test
    void updateCards_setsSpymasterPick() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/game/start"))
                .andExpect(status().isOk())
                .andReturn();

        List<CardResponse> returnedCards = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        int gameId = returnedCards.getFirst().getGameId();

        List<UUID> pickedIds = returnedCards.stream()
                .limit(3)
                .map(CardResponse::getId)
                .toList();

        String requestBody = objectMapper.writeValueAsString(
                new java.util.HashMap<>() {{
                    put("cardIds", pickedIds);
                    put("maxScore", 4);
                    put("hintContent", "testclue");
                }}
        );

        mockMvc.perform(post("/api/v1/game/" + gameId + "/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        List<Card> dbAllCards = cardRepository.findByGame_Id(gameId);
        List<Card> dbPickedCards = dbAllCards.stream()
                .filter(card -> pickedIds.contains(card.getId()))
                .toList();
        List<Card> dbUnpickedCards = dbAllCards.stream()
                .filter(card -> !pickedIds.contains(card.getId()))
                .toList();

        assertThat(dbPickedCards).isNotEmpty();
        assertThat(dbUnpickedCards).isNotEmpty();
        assertThat(dbPickedCards).allMatch(Card::isSpymasterPick);
        assertThat(dbUnpickedCards).noneMatch(Card::isSpymasterPick);
    }

    @Test
    void getGameList_returnsReadyGames() throws Exception {
        MvcResult startResult = mockMvc.perform(post("/api/v1/game/start"))
                .andExpect(status().isOk())
                .andReturn();

        List<CardResponse> returnedCards = objectMapper.readValue(
                startResult.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        int gameId = returnedCards.getFirst().getGameId();

        String submitBody = """
                { "cardIds": [], "maxScore": 0, "hintContent": "testclue" }
                """;

        mockMvc.perform(post("/api/v1/game/%d/submit".formatted(gameId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(submitBody))
                .andExpect(status().isOk());

        MvcResult listResult = mockMvc.perform(get("/api/v1/game/list"))
                .andExpect(status().isOk())
                .andReturn();

        List<GameResponse> games = objectMapper.readValue(
                listResult.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        assertThat(games).isNotEmpty();
        assertThat(games)
                .anyMatch(game -> game.getGameId() == gameId
                        && "testclue".equals(game.getHint()));
    }
}
