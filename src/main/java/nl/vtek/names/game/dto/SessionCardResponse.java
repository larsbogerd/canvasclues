package nl.vtek.names.game.dto;

import java.util.UUID;

public record SessionCardResponse(
        UUID id,
        Long gameId,
        String imageUrlTemplate,
        String title,
        String artistDisplay,
        UUID artworkId,
        String altText,
        String source
) {}
