package nl.vtek.names.game.dto;

import java.time.LocalDateTime;

public record GameResponse(
        Long gameId,
        String hint,
        int spyScore,
        LocalDateTime createdAt,
        int playCount,
        Double qualityRatio,
        String gameMode
) {}
