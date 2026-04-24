package nl.vtek.names.game.dto;

import java.util.List;
import java.util.UUID;

public record SubmitRequest(
        List<UUID> cardIds,
        Integer maxScore,
        String hintContent
) {}
