package nl.vtek.names.game.service;

import nl.vtek.names.game.dto.HintResponse;
import nl.vtek.names.game.dto.HintStatsListResponse;
import nl.vtek.names.game.exception.GameNotFoundException;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.HintRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HintServiceTest {

    @Mock
    private HintRepository hintRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private HintService hintService;

    @Test
    void createHint_savesTheHintLowercasedAgainstItsGame() {
        Game game = new Game();
        game.setId(7L);
        given(gameRepository.findById(7L)).willReturn(Optional.of(game));

        hintService.createHint(7L, "Zonnebloem");

        ArgumentCaptor<Hint> saved = ArgumentCaptor.forClass(Hint.class);
        verify(hintRepository).save(saved.capture());
        assertThat(saved.getValue().getContent()).isEqualTo("zonnebloem");
        assertThat(saved.getValue().getGame()).isSameAs(game);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "twee woorden", "rembrandt1669", "zee-gezicht"})
    void createHint_rejectsAnythingButOneWordOfLetters(String content) {
        given(gameRepository.findById(7L)).willReturn(Optional.of(new Game()));

        assertThatThrownBy(() -> hintService.createHint(7L, content))
                .isInstanceOf(InputMismatchException.class);

        verify(hintRepository, never()).save(any());
    }

    @Test
    void createHint_unknownGame_throwsNotFound() {
        given(gameRepository.findById(7L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> hintService.createHint(7L, "zon"))
                .isInstanceOf(GameNotFoundException.class);
    }

    @Test
    void getHintByGameId_mapsTheStoredHint() {
        Game game = new Game();
        game.setId(7L);
        given(hintRepository.findByGame_Id(7L)).willReturn(Optional.of(new Hint("zon", game)));

        Optional<HintResponse> result = hintService.getHintByGameId(7L);

        assertThat(result).hasValueSatisfying(hint -> {
            assertThat(hint.gameId()).isEqualTo(7L);
            assertThat(hint.content()).isEqualTo("zon");
        });
    }

    @Test
    void getHintOccurrence_countsEachHint() {
        given(hintRepository.findAll()).willReturn(List.of(
                new Hint("zon", null), new Hint("zee", null), new Hint("zon", null)));

        List<HintStatsListResponse> stats = hintService.getHintOccurrence();

        assertThat(stats)
                .extracting(HintStatsListResponse::hint, HintStatsListResponse::frequency)
                .containsExactlyInAnyOrder(tuple("zon", 2L), tuple("zee", 1L));
    }
}
