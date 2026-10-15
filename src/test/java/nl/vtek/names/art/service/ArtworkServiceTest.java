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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
        lenient().when(source.minPoolSize()).thenReturn(64);
    }

    @Test
    void drawArtworks_poolAtItsMinimum_neverCallsTheMuseum() {
        List<Artwork> pooled = artworks(16);
        given(artworkRepository.countBySource("met")).willReturn(64L);
        given(artworkRepository.findRandomBySource("met", 16)).willReturn(pooled);

        assertThat(artworkService.drawArtworks(16, source)).isSameAs(pooled);

        verify(source, never()).fetchArtworks(anyInt());
    }

    @Test
    void drawArtworks_poolBelowItsMinimum_fetchesLiveAndStoresTheResult() {
        given(artworkRepository.countBySource("met")).willReturn(40L);
        given(source.fetchArtworks(20)).willReturn(sourceArtworks(20));
        given(artworkRepository.findBySourceAndExternalImageId(eq("met"), anyString())).willReturn(Optional.empty());
        given(artworkRepository.save(any(Artwork.class))).willAnswer(call -> call.getArgument(0));

        List<Artwork> drawn = artworkService.drawArtworks(16, source);

        assertThat(drawn).hasSize(16).allMatch(artwork -> "met".equals(artwork.getSource()));
        verify(artworkRepository, times(16)).save(any(Artwork.class));
    }

    @Test
    void drawArtworks_liveFetch_reusesArtworksAlreadyStored() {
        Artwork stored = new Artwork();
        given(source.fetchArtworks(6)).willReturn(sourceArtworks(2));
        given(artworkRepository.findBySourceAndExternalImageId("met", "img-0")).willReturn(Optional.of(stored));
        given(artworkRepository.findBySourceAndExternalImageId("met", "img-1")).willReturn(Optional.empty());
        given(artworkRepository.save(any(Artwork.class))).willAnswer(call -> call.getArgument(0));

        List<Artwork> drawn = artworkService.drawArtworks(2, source);

        assertThat(drawn).hasSize(2).contains(stored);
        verify(artworkRepository, times(1)).save(any(Artwork.class));
    }

    @Test
    void drawArtworks_liveFetch_dropsArtworksWithoutAnImage() {
        SourceArtwork imageless = new SourceArtwork(null, "t", null, null, null, null, null, null, null, null, null);
        List<SourceArtwork> fetched = new ArrayList<>(sourceArtworks(2));
        fetched.addFirst(imageless);
        given(source.fetchArtworks(6)).willReturn(fetched);
        given(artworkRepository.findBySourceAndExternalImageId(eq("met"), anyString())).willReturn(Optional.empty());
        given(artworkRepository.save(any(Artwork.class))).willAnswer(call -> call.getArgument(0));

        assertThat(artworkService.drawArtworks(2, source))
                .extracting(Artwork::getExternalImageId)
                .containsExactlyInAnyOrder("img-0", "img-1");
    }

    @Test
    void topUpPool_fullPool_doesNothing() {
        given(artworkRepository.countBySource("met")).willReturn(200L);

        assertThat(artworkService.topUpPool(source, 200)).isZero();

        verify(source, never()).fetchArtworks(anyInt());
    }

    @Test
    void topUpPool_storesNewWarmedArtworksOnly() {
        given(artworkRepository.countBySource("met")).willReturn(10L);
        given(source.poolBatchSize()).willReturn(3);
        given(source.fetchArtworks(3)).willReturn(sourceArtworks(3));
        given(artworkRepository.findBySourceAndExternalImageId("met", "img-0")).willReturn(Optional.of(new Artwork()));
        given(artworkRepository.findBySourceAndExternalImageId("met", "img-1")).willReturn(Optional.empty());
        given(artworkRepository.findBySourceAndExternalImageId("met", "img-2")).willReturn(Optional.empty());
        given(source.warmImage("img-1")).willReturn(false);
        given(source.warmImage("img-2")).willReturn(true);
        given(artworkRepository.save(any(Artwork.class))).willAnswer(call -> call.getArgument(0));

        assertThat(artworkService.topUpPool(source, 200)).isEqualTo(1);

        verify(source, never()).warmImage("img-0");
        verify(artworkRepository).save(any(Artwork.class));
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

    private static List<Artwork> artworks(int count) {
        return Collections.nCopies(count, new Artwork());
    }

    private static List<SourceArtwork> sourceArtworks(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new SourceArtwork("img-" + i, "Title " + i, "Artist", null, null, null,
                        null, null, null, null, null))
                .toList();
    }
}
