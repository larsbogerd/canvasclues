package nl.vtek.names.art.mapper;

import nl.vtek.names.art.dto.ArticDto;
import nl.vtek.names.art.dto.ArtWorkResponse;
import nl.vtek.names.art.model.ArtWork;
import org.springframework.stereotype.Component;

@Component
public class ArtWorkMapper {

    private static final String IIIF_ARTIC_BASE_URL = "https://www.artic.edu/iiif/2";


    public ArtWork toEntity(ArticDto dto) {
        return new ArtWork(
                dto.id(),
                dto.imageId(),
                dto.title(),
                dto.artistDisplay(),
                dto.dateDisplay(),
                dto.mediumDisplay(),
                dto.placeOfOrigin()
        );
    }

    public ArtWorkResponse toResponse(ArtWork artWork, int id) {
        String imageUrl = "%s/%s/full/!500,500/0/default.jpg".formatted(IIIF_ARTIC_BASE_URL, artWork.getImageId());

        return new ArtWorkResponse(id, artWork.getTitle(), artWork.getArtistDisplay(),
                artWork.getDateDisplay(), artWork.getMediumDisplay(),
                artWork.getPlaceOfOrigin(), imageUrl);
    }
}
