package nl.vtek.names.game.dto;

import java.util.List;
import java.util.UUID;

public record SessionResponse(
        UUID sessionId,
        List<CardResponse> cards,
        HintResponse hint,
        int spymasterPickCount
)
{}
