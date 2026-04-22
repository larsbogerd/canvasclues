package nl.vtek.names.service;

import nl.vtek.names.art.dto.ArtWorkResponse;
import nl.vtek.names.art.service.ArtWorkService;
import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.repository.CardRepository;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ArtWorkService artWorkService;

    @InjectMocks
    private GameService gameService;

//    @Test
//    void startGame_createsAndSaves16Cards() {
//        List<ArtWorkResponse> fakeArtworks = buildFakeArtworks();
//        Game fakeGame = new Game();
//        fakeGame.setId(1);
//
//        given(artWorkService.searchArtworks(GameService.BOARD_SIZE)).willReturn(fakeArtworks);
//        given(gameRepository.save(any(Game.class))).willReturn(fakeGame);
//        given(cardRepository.saveAll(anyList())).willAnswer(inv -> inv.getArgument(0));
//
//        List<CardResponse> result = gameService.startGame();
//
//        assertThat(result).hasSize(GameService.BOARD_SIZE);
//        verify(artWorkService).searchArtworks(GameService.BOARD_SIZE);
//        verify(gameRepository).save(any(Game.class));
//        verify(cardRepository).saveAll(argThat(cards -> ((List<Card>) cards).size() == GameService.BOARD_SIZE));
//
//        int gameId = result.getFirst().getGameId();
//        assertThat(gameId).isNotZero();
//        assertThat(result).allMatch(card -> card.getGameId() == gameId);
//    }


//    private List<ArtWorkResponse> buildFakeArtworks() {
//        List<ArtWorkResponse> artworks = new ArrayList<>();
//        for (int i = 1; i <= GameService.BOARD_SIZE; i++) {
//            String title = "Painting " + i;
//            String artistDisplay = "Tester";
//            String dateDisplay = "2024";
//            artworks.add(new ArtWorkResponse(
//                    i,
//                    title,
//                    artistDisplay,
//                    dateDisplay,
//                    "https://image.url/" + i
//            ));
//        }
//        return artworks;
//    }


}
