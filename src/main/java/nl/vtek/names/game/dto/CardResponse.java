package nl.vtek.names.game.dto;

import nl.vtek.names.game.model.CardType;

import java.util.UUID;

public record CardResponse(
        UUID id,
        Long gameId,
        CardType type,
        String imageUrl,
        String title,
        String artistDisplay,
        UUID artworkId,
        String altText
) {}
