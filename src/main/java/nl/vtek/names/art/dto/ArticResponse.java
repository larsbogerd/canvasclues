package nl.vtek.names.art.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ArticResponse(@JsonProperty("data") List<ArticDto> pulledData
) {}