package nl.vtek.names.game.dto;

public record GameQualityRequest(
        Long gameId,
        boolean rating
){}
