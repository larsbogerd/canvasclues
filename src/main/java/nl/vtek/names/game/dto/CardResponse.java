package nl.vtek.names.game.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.vtek.names.game.model.CardType;

import java.util.UUID;

public record CardResponse(
        UUID id,
        Long gameId,
        CardType type,
        String imageUrl,
        String title,
        String artistDisplay,
        String dateDisplay,
        String mediumDisplay,
        String placeOfOrigin,
        String altText,
        @JsonProperty("spymasterPick")
        boolean isSpymasterPick
) {}
