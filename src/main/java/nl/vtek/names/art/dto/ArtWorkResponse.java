package nl.vtek.names.art.dto;

public record ArtWorkResponse(
        int id,
        String title,
        String artistDisplay,
        String dateDisplay,
        String mediumDisplay,
        String placeOfOrigin,
        String imageUrl
) {}
