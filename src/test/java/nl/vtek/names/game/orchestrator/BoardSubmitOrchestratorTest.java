package nl.vtek.names.game.orchestrator;

import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.game.dto.BoardSubmitRequest;
import nl.vtek.names.game.exception.MinimumSelectedException;
import nl.vtek.names.game.service.CardService;
import nl.vtek.names.game.service.GameService;
import nl.vtek.names.game.service.HintService;
import nl.vtek.names.game.service.ScoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class BoardSubmitOrchestratorTest {

    @Mock
    private GameService gameService;

    @Mock
    private CardService cardService;

    @Mock
    private HintService hintService;

    @Mock
    private ArtworkService artworkService;

    @Mock
    private ScoreService scoreService;

    @InjectMocks
    private BoardSubmitOrchestrator orchestrator;

    @Test
    void submit_validPicks_publishesTheBoard() {
        List<UUID> cardIds = List.of(UUID.randomUUID(), UUID.randomUUID());

        orchestrator.submit(1L, new BoardSubmitRequest(cardIds, 2, "zee"));

        verify(scoreService).handleSpyScore(1L, 2);
        verify(cardService).updateCards(cardIds, true);
        verify(hintService).createHint(1L, "zee");
        verify(artworkService).recordArtworkUsage(1L);
        verify(gameService).markReady(1L);
    }

    @Test
    void submit_tooFewPicks_publishesNothing() {
        willThrow(new MinimumSelectedException()).given(scoreService).handleSpyScore(1L, 1);

        assertThatThrownBy(() -> orchestrator.submit(1L,
                new BoardSubmitRequest(List.of(UUID.randomUUID()), 1, "zee")))
                .isInstanceOf(MinimumSelectedException.class);

        verifyNoInteractions(cardService, hintService, artworkService, gameService);
    }
}
