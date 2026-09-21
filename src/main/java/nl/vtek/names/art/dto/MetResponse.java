package nl.vtek.names.art.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** The Met's listing endpoint returns object ids only; each one is fetched separately. */
public record MetResponse(int total, @JsonProperty("objectIDs") List<Integer> objectIds) {}
