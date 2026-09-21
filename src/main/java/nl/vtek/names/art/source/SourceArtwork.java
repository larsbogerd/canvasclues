package nl.vtek.names.art.source;

public record SourceArtwork(
        String imageId,
        String title,
        String artistDisplay,
        String dateDisplay,
        String mediumDisplay,
        String placeOfOrigin,
        String dimensions,
        String departmentTitle,
        String styleTitle,
        String artworkTypeTitle,
        String shortDescription
) {}
