package nl.vtek.names.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

import nl.vtek.names.game.dto.HintResponse;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.GameRepository;
import nl.vtek.names.game.repository.HintRepository;
import nl.vtek.names.game.service.HintService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class HintServiceTest {

    @Mock
    private HintRepository hintRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private HintService hintService;

    @Test
    void createHint_savesCorrectGameIdAndContent() {
        Game game = new Game();
        game.setId(111111L);

        given(gameRepository.findById(111111L)).willReturn(Optional.of(game));

        hintService.createHint(111111L, "testhint");

        ArgumentCaptor<Hint> captor = ArgumentCaptor.forClass(Hint.class);
        verify(hintRepository).save(captor.capture());

        Hint saved = captor.getValue();
        assertThat(saved.getGame().getId()).isEqualTo(111111L);
        assertThat(saved.getContent()).isEqualTo("testhint");
    }

    @Test
    void getHintByGameId_returnsHint() {
        Game game = new Game();
        game.setId(111111L);
        Hint hint = new Hint("testhint", game);

        given(hintRepository.findByGame_Id(111111L)).willReturn(Optional.of(hint));

        Optional<HintResponse> result = hintService.getHintByGameId(111111L);

        assertThat(result).isPresent();
        assertThat(result.get().content()).isEqualTo("testhint");
        verify(hintRepository).findByGame_Id(111111L);
    }
}
