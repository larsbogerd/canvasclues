package nl.vtek.names.art.dto;

import java.util.UUID;

public record ArtworkDetailsResponse(
        UUID id,
        String dateDisplay,
        String mediumDisplay,
        String placeOfOrigin,
        String departmentTitle,
        String dimensions,
        String styleTitle,
        String artworkTypeTitle,
        String shortDescription
) {}
