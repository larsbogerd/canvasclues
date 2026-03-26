package nl.vtek.names.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

import nl.vtek.names.game.dto.HintRequest;
import nl.vtek.names.game.model.Hint;
import nl.vtek.names.game.repository.HintRepo;
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
    private HintRepo hintRepo;

    @InjectMocks
    private HintService hintService;

    @Test
    void createHint_savesCorrectGameIdAndContent() {
        HintRequest request = new HintRequest();
        request.setGameId(111111);
        request.setHintContent("testhint");

        hintService.createHint(request);

        ArgumentCaptor<Hint> captor = ArgumentCaptor.forClass(Hint.class);
        verify(hintRepo).save(captor.capture());

        Hint saved = captor.getValue();
        assertThat(saved.getGameId()).isEqualTo(111111);
        assertThat(saved.getHintContent()).isEqualTo("testhint");
    }

    @Test
    void getHintByGameId_returnsHint() {
        Hint hint = new Hint("testhint", 1);
        given(hintRepo.findByGameId(111111)).willReturn(Optional.of(hint));

        Optional<Hint> result = hintService.getHintByGameId(111111);

        assertThat(result).isPresent();
        assertThat(result.get().getHintContent()).isEqualTo("testhint");
        verify(hintRepo).findByGameId(111111);
    }
}
