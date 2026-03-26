package nl.vtek.names.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nl.vtek.names.game.service.GameService;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import nl.vtek.names.game.model.GameCard;
import nl.vtek.names.game.repository.GameCardRepository;
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
    private GameCardRepository gameCardRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void startGame_returns16CardsFromDb() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/game/start"))
                .andExpect(status().isOk())
                .andReturn();

        List<GameCard> returnedCards = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        assertThat(returnedCards).hasSize(GameService.BOARD_SIZE);

        int gameId = returnedCards.getFirst().getGameId();
        assertThat(gameId).isNotZero();
        assertThat(returnedCards).allMatch(card -> card.getGameId() == gameId);

        assertThat(returnedCards).allSatisfy(card -> {
            assertThat(card.getImageUrl()).isNotEmpty();
            assertThat(card.getAltText()).isNotEmpty();
        });

        List<GameCard> dbCards = gameCardRepository.findByGameId(gameId);
        assertThat(dbCards).hasSize(GameService.BOARD_SIZE);
    }

    @Test
    void getGame_returnsCreatedCards() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/game/start"))
                .andExpect(status().isOk())
                .andReturn();

        List<GameCard> returnedCards = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        int gameId = returnedCards.getFirst().getGameId();

        MvcResult getResult = mockMvc.perform(get("/api/v1/game/" + gameId))
                .andExpect(status().isOk())
                .andReturn();

        List<GameCard> fetchedCards = objectMapper.readValue(
                getResult.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        assertThat(fetchedCards).hasSize(GameService.BOARD_SIZE);
        assertThat(fetchedCards).allMatch(card -> card.getGameId() == gameId);

        List<UUID> createdIds = returnedCards.stream().map(GameCard::getId).toList();
        List<UUID> fetchedIds = fetchedCards.stream().map(GameCard::getId).toList();
        assertThat(fetchedIds).containsExactlyInAnyOrderElementsOf(createdIds);
    }

    @Test
    void updateCards_setsSpymasterPick() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/game/start"))
                .andExpect(status().isOk())
                .andReturn();

        List<GameCard> returnedCards = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        int gameId = returnedCards.getFirst().getGameId();

        List<UUID> pickedIds = returnedCards.stream()
                .limit(3)
                .map(GameCard::getId)
                .toList();

        String requestBody = objectMapper.writeValueAsString(
                new java.util.HashMap<>() {{
                    put("cardIds", pickedIds);
                    put("spymasterPick", true);
                }}
        );

        MvcResult updateResult = mockMvc.perform(patch("/api/v1/game/updatecards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        List<GameCard> pickedCards = objectMapper.readValue(
                updateResult.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        assertThat(pickedCards).hasSize(3);
        assertThat(pickedCards).allMatch(GameCard::isSpymasterPick);

        List<GameCard> dbAllCards = gameCardRepository.findByGameId(gameId);
        List<GameCard> dbPickedCards = dbAllCards.stream()
                .filter(card -> pickedIds.contains(card.getId()))
                .toList();
        List<GameCard> dbUnpickedCards = dbAllCards.stream()
                .filter(card -> !pickedIds.contains(card.getId()))
                .toList();

        assertThat(dbPickedCards).isNotEmpty();
        assertThat(dbUnpickedCards).isNotEmpty();
        assertThat(dbPickedCards).allMatch(GameCard::isSpymasterPick);
        assertThat(dbUnpickedCards).noneMatch(GameCard::isSpymasterPick);
    }
}
