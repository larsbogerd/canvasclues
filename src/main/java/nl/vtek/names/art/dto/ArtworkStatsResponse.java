package nl.vtek.names.art.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ArtworkStatsResponse(
        UUID id,
        String title,
        String artistDisplay,
        String dateDisplay,
        String mediumDisplay,
        String placeOfOrigin,
        String dimensions,
        String departmentTitle,
        int timesLoaded,
        int timesSpymasterPick,
        int timesCorrectGuess,
        int timesBadGuess,
        Double pickPercentage,
        LocalDateTime firstUsedAt,
        LocalDateTime lastUsedAt
) {}
