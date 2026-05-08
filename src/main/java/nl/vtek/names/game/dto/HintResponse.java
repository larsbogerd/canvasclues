package nl.vtek.names.game.dto;

import java.util.UUID;

public record HintResponse(
        UUID id,
        Long gameId,
        String content
) {
}
