package nl.vtek.names.game.orchestrator;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.service.ArtworkService;
import nl.vtek.names.art.source.ArtSource;
import nl.vtek.names.art.source.ArtSourceRegistry;
import nl.vtek.names.game.dto.CardResponse;
import nl.vtek.names.game.exception.UnknownGameModeException;
import nl.vtek.names.game.mapper.CardMapper;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.service.CardService;
import nl.vtek.names.game.service.GameModeRegistry;
import nl.vtek.names.game.service.GameService;
import nl.vtek.names.game.strategy.EasyModeStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class StartGameOrchestratorTest {

    @Mock
    private GameService gameService;

    @Mock
    private ArtworkService artworkService;

    @Mock
    private CardService cardService;

    @Mock
    private GameModeRegistry gameModeRegistry;

    @Mock
    private ArtSourceRegistry artSources;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private StartGameOrchestrator orchestrator;

    @Test
    void startGame_drawsArtworksFromTheResolvedSourceAndRecordsIt() {
        EasyModeStrategy mode = new EasyModeStrategy();
        ArtSource source = mock(ArtSource.class);
        given(source.key()).willReturn("met");
        List<Artwork> artworks = List.of(new Artwork());
        Game game = new Game();
        List<Card> cards = List.of(new Card());
        List<CardResponse> responses = List.of();
        given(gameModeRegistry.getMode("makkelijk")).willReturn(mode);
        given(artSources.resolve("met")).willReturn(source);
        given(artworkService.drawArtworks(16, source)).willReturn(artworks);
        given(gameService.createGame("makkelijk", "met")).willReturn(game);
        given(cardService.buildBoard(game, artworks, mode)).willReturn(cards);
        given(cardMapper.toCardResponse(cards)).willReturn(responses);

        assertThat(orchestrator.startGame("makkelijk", "met")).isSameAs(responses);
    }

    @Test
    void startGame_unknownMode_failsBeforeTouchingAMuseum() {
        given(gameModeRegistry.getMode("extreem")).willThrow(new UnknownGameModeException());

        assertThatThrownBy(() -> orchestrator.startGame("extreem", null))
                .isInstanceOf(UnknownGameModeException.class);

        verifyNoInteractions(artworkService, gameService);
    }

    @Test
    void startGame_failedFetch_createsNoGame() {
        ArtSource source = mock(ArtSource.class);
        given(gameModeRegistry.getMode("makkelijk")).willReturn(new EasyModeStrategy());
        given(artSources.resolve(null)).willReturn(source);
        given(artworkService.drawArtworks(any(Integer.class), any())).willThrow(new RuntimeException("museum down"));

        assertThatThrownBy(() -> orchestrator.startGame("makkelijk", null)).hasMessage("museum down");

        verifyNoInteractions(gameService);
    }
}
