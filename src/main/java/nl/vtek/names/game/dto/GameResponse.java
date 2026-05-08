package nl.vtek.names.game.dto;

import java.time.LocalDateTime;

public record GameResponse(
        Long gameId,
        String hint,
        int maxScore,
        LocalDateTime createdAt,
        int playCount
) {}
