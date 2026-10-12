package nl.vtek.names.art.mapper;

import nl.vtek.names.art.dto.ArtworkStatsListResponse;
import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.source.ArtSourceRegistry;
import nl.vtek.names.art.source.SourceArtwork;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArtworkMapperTest {

    @Mock
    private ArtSourceRegistry artSources;

    @InjectMocks
    private ArtworkMapper mapper;

    @Test
    void toEntity_keepsTheSourceAndImageId() {
        SourceArtwork dto = new SourceArtwork("img-1", "De Nachtwacht", "Rembrandt", "1642",
                "Olieverf", "Amsterdam", "363 × 437 cm", "Schilderkunst", null, null, null);

        Artwork artwork = mapper.toEntity(dto, "met");

        assertThat(artwork.getSource()).isEqualTo("met");
        assertThat(artwork.getExternalImageId()).isEqualTo("img-1");
        assertThat(artwork.getTitle()).isEqualTo("De Nachtwacht");
        assertThat(artwork.getTimesLoaded()).isZero();
    }

    @Test
    void toStatsResponse_roundsPercentagesToOneDecimal() {
        Artwork artwork = new Artwork();
        artwork.setTimesLoaded(3);
        artwork.setTimesSpymasterPick(1);
        artwork.setTimesCorrectGuess(2);
        artwork.setTimesBadGuess(1);
        given(artSources.imageUrlTemplate(artwork)).willReturn("/img/cma/x.jpg");

        ArtworkStatsListResponse stats = mapper.toStatsResponse(artwork);

        assertThat(stats.pickPercentage()).isEqualTo(33.3);
        assertThat(stats.guessPercentage()).isEqualTo(66.7);
        assertThat(stats.imageUrlTemplate()).isEqualTo("/img/cma/x.jpg");
    }

    @Test
    void toStatsResponse_unusedArtworkHasNoPercentages() {
        Artwork artwork = new Artwork();

        ArtworkStatsListResponse stats = mapper.toStatsResponse(artwork);

        assertThat(stats.pickPercentage()).isNull();
        assertThat(stats.guessPercentage()).isNull();
    }
}
