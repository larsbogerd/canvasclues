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
                dto.dateDisplay()
        );
    }

    public ArtWorkResponse toResponse(ArtWork artWork, int position) {
        String imageUrl = "%s/%s/full/!500,500/0/default.jpg".formatted(IIIF_ARTIC_BASE_URL, artWork.getImageId());
        String altText = "%s — %s, %s".formatted(
                artWork.getTitle(), artWork.getArtistDisplay(), artWork.getDateDisplay());

        return new ArtWorkResponse(position, imageUrl, altText);
    }
}
