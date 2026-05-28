package nl.vtek.names.art.mapper;

import nl.vtek.names.art.dto.ArticDto;
import nl.vtek.names.art.dto.ArtworkResponse;
import nl.vtek.names.art.dto.ArtworkStatsListResponse;
import nl.vtek.names.art.dto.ArtworkStatsResponse;
import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.util.IiifUrlBuilder;
import org.springframework.stereotype.Component;

@Component
public class ArtworkMapper {

    public Artwork toEntity(ArticDto dto) {
        return new Artwork(
                dto.id(),
                dto.title(),
                dto.artistDisplay(),
                dto.dateDisplay(),
                dto.mediumDisplay(),
                dto.placeOfOrigin(),
                dto.dimensions(),
                dto.departmentTitle()
        );
    }

    public ArtworkResponse toResponse(Artwork artwork) {
        return new ArtworkResponse(artwork.getId(), artwork.getTitle(), artwork.getArtistDisplay(),
                artwork.getDateDisplay(), artwork.getMediumDisplay(),
                artwork.getPlaceOfOrigin(), IiifUrlBuilder.forArtwork(artwork.getId()));
    }

    public ArtworkStatsListResponse toStatsResponse(Artwork artwork) {
        return new ArtworkStatsListResponse(
                artwork.getId(),
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
