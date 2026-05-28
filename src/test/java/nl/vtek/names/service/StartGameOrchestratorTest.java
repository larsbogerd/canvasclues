package nl.vtek.names.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.CardType;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.service.CardService;
import nl.vtek.names.game.service.GameService;
import nl.vtek.names.game.orchestrator.StartGameOrchestrator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class StartGameOrchestratorTest {

    @Mock
    private GameService gameService;

    @Mock
    private ArtworkService artworkService;

    @Mock
    private CardService cardService;

    @InjectMocks
    private StartGameOrchestrator startGameOrchestrator;


    @Test
    void startGame_createsAndSaves16Cards() {
        Game fakeGame = new Game();
        fakeGame.setId(1L);

        List<Artwork> fakeArtworks = buildFakeArtworks();
        List<Card> fakeCards = buildFakeCards(fakeGame, fakeArtworks);

        given(gameService.createGame()).willReturn(fakeGame);
        given(artworkService.fetchAndSaveArtworks(StartGameOrchestrator.BOARD_SIZE)).willReturn(fakeArtworks);
        given(cardService.buildBoard(any(Game.class), anyList(), anyInt())).willReturn(fakeCards);

        List<CardResponse> result = startGameOrchestrator.startGame();

        assertThat(result).hasSize(StartGameOrchestrator.BOARD_SIZE);
        verify(gameService).createGame();
        verify(artworkService).fetchAndSaveArtworks(StartGameOrchestrator.BOARD_SIZE);
        verify(cardService).buildBoard(fakeGame, fakeArtworks, StartGameOrchestrator.BOARD_SIZE);


        Long gameId = result.getFirst().gameId();
        assertThat(gameId).isNotZero();
        assertThat(result).allMatch(card -> card.gameId().equals(gameId));
    }

    private List<Artwork> buildFakeArtworks() {
        List<Artwork> artworks = new ArrayList<>();
        for (int i = 1; i <= StartGameOrchestrator.BOARD_SIZE; i++) {
            artworks.add(new Artwork(
                    UUID.randomUUID(),
                    "Painting " + i,
                    "Tester",
                    "2024",
                    "Oil on canvas",
                    "Somewhere",
                    "10x10",
                    "Department"
            ));
        }
        return artworks;
    }

    private List<Card> buildFakeCards(Game game, List<Artwork> artworks) {
        List<Card> cards = new ArrayList<>();
        for (Artwork artwork : artworks) {
            Card card = new Card(game, CardType.PLAYABLE);
            card.setArtwork(artwork);
            cards.add(card);
        }
        return cards;
    }
}
