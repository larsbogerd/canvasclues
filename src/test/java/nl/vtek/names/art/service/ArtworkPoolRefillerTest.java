package nl.vtek.names.art.service;

import nl.vtek.names.art.source.ArtSource;
import nl.vtek.names.art.source.ArtSourceRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtworkPoolRefillerTest {

    @Mock
    private ArtSourceRegistry artSources;

    @Mock
    private ArtworkService artworkService;

    @Test
    void refill_oneFailingMuseumDoesNotStarveTheOthers() {
        ArtSource artic = mock(ArtSource.class);
        ArtSource cma = mock(ArtSource.class);
        given(artic.key()).willReturn("artic");
        given(artSources.all()).willReturn(List.of(artic, cma));
        given(artworkService.topUpPool(eq(artic), eq(150), anyInt())).willThrow(new RuntimeException("521"));

        new ArtworkPoolRefiller(artSources, artworkService, 150).refill();

        verify(artworkService).topUpPool(eq(cma), eq(150), anyInt());
    }
}
