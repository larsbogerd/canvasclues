package nl.vtek.names.art.mapper;

import nl.vtek.names.art.dto.ArtworkDetailsResponse;
import nl.vtek.names.art.dto.ArtworkStatsListResponse;
import nl.vtek.names.art.dto.ArtworkStatsResponse;
import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.source.ArtSourceRegistry;
import nl.vtek.names.art.source.SourceArtwork;
import org.springframework.stereotype.Component;

@Component
public class ArtworkMapper {

    private final ArtSourceRegistry artSources;

    public ArtworkMapper(ArtSourceRegistry artSources) {
        this.artSources = artSources;
    }

    public Artwork toEntity(SourceArtwork dto, String source) {
        return new Artwork(
                source,
                dto.imageId(),
                dto.title(),
                dto.artistDisplay(),
                dto.dateDisplay(),
                dto.mediumDisplay(),
                dto.placeOfOrigin(),
                dto.dimensions(),
                dto.departmentTitle(),
                dto.styleTitle(),
                dto.artworkTypeTitle(),
                dto.shortDescription()
        );
    }


    public ArtworkDetailsResponse toDetailsResponse(Artwork artwork) {
        return new ArtworkDetailsResponse(
                artwork.getId(),
                artwork.getDateDisplay(),
                artwork.getMediumDisplay(),
                artwork.getPlaceOfOrigin(),
                artwork.getDepartmentTitle(),
                artwork.getDimensions(),
                artwork.getStyleTitle(),
                artwork.getArtworkTypeTitle(),
                artwork.getShortDescription()
        );
    }

    public ArtworkStatsListResponse toStatsResponse(Artwork artwork) {
        return new ArtworkStatsListResponse(
                artwork.getId(),
                artSources.imageUrlTemplate(artwork),
                artwork.getTitle(),
                artwork.getArtistDisplay(),
                artwork.getTimesLoaded(),
                artwork.getTimesSpymasterPick(),
                artwork.getTimesCorrectGuess(),
                artwork.getTimesBadGuess(),
                calculatePickPercentage(artwork),
                calculateGuessPercentage(artwork),
                artwork.getFirstUsedAt(),
                artwork.getLastUsedAt()
        );
    }

    public ArtworkStatsResponse toDetailResponse(Artwork artwork) {
        return new ArtworkStatsResponse(
                artwork.getId(),
                artSources.imageUrlTemplate(artwork),
                artwork.getTitle(),
                artwork.getArtistDisplay(),
                artwork.getDateDisplay(),
                artwork.getMediumDisplay(),
                artwork.getPlaceOfOrigin(),
                artwork.getDimensions(),
                artwork.getDepartmentTitle(),
                artwork.getTimesLoaded(),
                artwork.getTimesSpymasterPick(),
                artwork.getTimesCorrectGuess(),
                artwork.getTimesBadGuess(),
                calculatePickPercentage(artwork),
                calculateGuessPercentage(artwork),
                artwork.getFirstUsedAt(),
                artwork.getLastUsedAt()
        );
    }

    private Double calculatePickPercentage(Artwork artwork) {
        int loaded = artwork.getTimesLoaded();
        int picked = artwork.getTimesSpymasterPick();
        return loaded == 0 ? null : Math.round((picked / (double) loaded) * 1000) / 10.0;
    }

    private Double calculateGuessPercentage(Artwork artwork) {
        int correct = artwork.getTimesCorrectGuess();
        int bad = artwork.getTimesBadGuess();
        int total = correct + bad;
        return total == 0 ? null : Math.round((correct / (double) total) * 1000) / 10.0;
    }
}
