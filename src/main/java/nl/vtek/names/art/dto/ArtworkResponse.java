package nl.vtek.names.art.dto;

import java.util.UUID;

public record ArtworkResponse(
        UUID id,
        String title,
        String artistDisplay,
        String dateDisplay,
        String mediumDisplay,
        String placeOfOrigin,
        String imageUrl
) {}
