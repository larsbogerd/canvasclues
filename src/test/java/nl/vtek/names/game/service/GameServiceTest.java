package nl.vtek.names.game.service;

import nl.vtek.names.game.dto.GameQualityRequest;
import nl.vtek.names.game.dto.GameResponse;
import nl.vtek.names.game.exception.GameNotFoundException;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.GameState;
import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    void createGame_startsInCreatingWithModeAndSource() {
        given(gameRepository.save(any(Game.class))).willAnswer(call -> call.getArgument(0));

        Game game = gameService.createGame("gemiddeld", "met");

        assertThat(game.getState()).isEqualTo(GameState.CREATING);
        assertThat(game.getGameMode()).isEqualTo("gemiddeld");
        assertThat(game.getSource()).isEqualTo("met");
    }

    @Test
    void markReady_unknownGame_throwsNotFound() {
        given(gameRepository.findById(3L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> gameService.markReady(3L)).isInstanceOf(GameNotFoundException.class);
    }

    @Test
    void updateRating_countsLikesAndDislikes() {
        Game game = new Game();
        given(gameRepository.findById(3L)).willReturn(Optional.of(game));

        gameService.updateRating(new GameQualityRequest(3L, true));
        gameService.updateRating(new GameQualityRequest(3L, true));
        gameService.updateRating(new GameQualityRequest(3L, false));

        assertThat(game.getLikes()).isEqualTo(2);
        assertThat(game.getDislikes()).isEqualTo(1);
    }

    @Test
    void getGameList_reportsHintSourceAndQualityRatio() {
        Game game = new Game();
        game.setId(3L);
        game.setSource("cma");
        game.setLikes(2);
        game.setDislikes(1);
        game.setHints(List.of(new Hint("zon", game)));
        Game unrated = new Game();
        unrated.setHints(List.of());
        given(gameRepository.findByState(GameState.READY)).willReturn(List.of(game, unrated));

        List<GameResponse> list = gameService.getGameList();

        assertThat(list.getFirst().hint()).isEqualTo("zon");
        assertThat(list.getFirst().source()).isEqualTo("cma");
        assertThat(list.getFirst().qualityRatio()).isEqualTo(66.7);
        assertThat(list.get(1).hint()).isNull();
        assertThat(list.get(1).qualityRatio()).isNull();
    }
}
