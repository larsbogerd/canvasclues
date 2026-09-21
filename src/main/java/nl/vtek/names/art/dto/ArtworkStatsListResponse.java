package nl.vtek.names.art.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ArtworkStatsListResponse(
        UUID id,
        String imageId,
        String title,
        String artistDisplay,
        int timesLoaded,
        int timesSpymasterPick,
        int timesCorrectGuess,
        int timesBadGuess,
        Double pickPercentage,
        Double guessPercentage,
        LocalDateTime firstUsedAt,
        LocalDateTime lastUsedAt
) {}
