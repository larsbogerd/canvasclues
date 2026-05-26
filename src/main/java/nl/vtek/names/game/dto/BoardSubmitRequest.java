package nl.vtek.names.game.dto;

import java.util.List;
import java.util.UUID;

public record BoardSubmitRequest(
        List<UUID> cardIds,
        Integer spyScore,
        String hintContent
) {}
