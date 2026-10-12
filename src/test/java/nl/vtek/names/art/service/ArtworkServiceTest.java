package nl.vtek.names.art.service;

import nl.vtek.names.art.mapper.ArtworkMapper;
import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.repository.ArtworkRepository;
import nl.vtek.names.art.source.ArtSource;
import nl.vtek.names.art.source.ArtSourceRegistry;
import nl.vtek.names.art.source.SourceArtwork;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.CardType;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtworkServiceTest {

    @Mock
    private ArtworkRepository artworkRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ArtSource source;

    private ArtworkService artworkService;

    @BeforeEach
    void setUp() {
        artworkService = new ArtworkService(
                new ArtworkMapper(mock(ArtSourceRegistry.class)), artworkRepository, gameRepository);
        lenient().when(source.key()).thenReturn("met");
    }

    @Test
    void fetchAndSaveArtworks_storesWhatTheMuseumReturns() {
        given(source.fetchArtworks(20)).willReturn(sourceArtworks(20));
        given(artworkRepository.findBySourceAndExternalImageId(eq("met"), anyString())).willReturn(Optional.empty());
        given(artworkRepository.save(any(Artwork.class))).willAnswer(call -> call.getArgument(0));

        List<Artwork> saved = artworkService.fetchAndSaveArtworks(16, source);

        assertThat(saved).hasSize(16).allMatch(artwork -> "met".equals(artwork.getSource()));
        verify(artworkRepository, times(16)).save(any(Artwork.class));
    }

    @Test
    void fetchAndSaveArtworks_reusesArtworksAlreadyStored() {
        Artwork stored = new Artwork();
        given(source.fetchArtworks(6)).willReturn(sourceArtworks(2));
        given(artworkRepository.findBySourceAndExternalImageId("met", "img-0")).willReturn(Optional.of(stored));
        given(artworkRepository.findBySourceAndExternalImageId("met", "img-1")).willReturn(Optional.empty());
        given(artworkRepository.save(any(Artwork.class))).willAnswer(call -> call.getArgument(0));

        List<Artwork> saved = artworkService.fetchAndSaveArtworks(2, source);

        assertThat(saved).hasSize(2).contains(stored);
        verify(artworkRepository, times(1)).save(any(Artwork.class));
    }

    @Test
    void fetchAndSaveArtworks_dropsArtworksWithoutAnImage() {
        SourceArtwork imageless = new SourceArtwork(null, "t", null, null, null, null, null, null, null, null, null);
        List<SourceArtwork> fetched = new ArrayList<>(sourceArtworks(2));
        fetched.addFirst(imageless);
        given(source.fetchArtworks(6)).willReturn(fetched);
        given(artworkRepository.findBySourceAndExternalImageId(eq("met"), anyString())).willReturn(Optional.empty());
        given(artworkRepository.save(any(Artwork.class))).willAnswer(call -> call.getArgument(0));

        assertThat(artworkService.fetchAndSaveArtworks(2, source))
                .extracting(Artwork::getExternalImageId)
                .containsExactlyInAnyOrder("img-0", "img-1");
    }

    @Test
    void recordArtworkUsage_countsLoadsAndPicksAndStampsDates() {
        Artwork picked = new Artwork();
        Artwork unpicked = new Artwork();
        Game game = new Game();
        Card pickedCard = new Card(game, CardType.PLAYABLE);
        pickedCard.setArtwork(picked);
        pickedCard.setSpymasterPick(true);
        Card unpickedCard = new Card(game, CardType.ASSASSIN);
        unpickedCard.setArtwork(unpicked);
        game.setCards(List.of(pickedCard, unpickedCard));
        given(gameRepository.findById(1L)).willReturn(Optional.of(game));

        artworkService.recordArtworkUsage(1L);

        assertThat(picked.getTimesLoaded()).isEqualTo(1);
        assertThat(picked.getTimesSpymasterPick()).isEqualTo(1);
        assertThat(unpicked.getTimesLoaded()).isEqualTo(1);
        assertThat(unpicked.getTimesSpymasterPick()).isZero();
        assertThat(picked.getFirstUsedAt()).isNotNull().isEqualTo(picked.getLastUsedAt());
    }

    @Test
    void recordGuessUsage_countsCorrectAndBadGuesses() {
        Artwork artwork = new Artwork();

        artworkService.recordGuessUsage(artwork, true);
        artworkService.recordGuessUsage(artwork, false);
        artworkService.recordGuessUsage(artwork, false);

        assertThat(artwork.getTimesCorrectGuess()).isEqualTo(1);
        assertThat(artwork.getTimesBadGuess()).isEqualTo(2);
    }

    private static List<SourceArtwork> sourceArtworks(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new SourceArtwork("img-" + i, "Title " + i, "Artist", null, null, null,
                        null, null, null, null, null))
                .toList();
    }
}
