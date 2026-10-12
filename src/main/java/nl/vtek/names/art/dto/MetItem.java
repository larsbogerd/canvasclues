package nl.vtek.names.art.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MetItem(
        String title,
        @JsonProperty("artistDisplayName") String artistDisplayName,
        @JsonProperty("objectDate") String objectDate,
        String medium,
        String dimensions,
        String department,
        String culture,
        @JsonProperty("isPublicDomain") boolean isPublicDomain,
        @JsonProperty("primaryImageSmall") String primaryImageSmall
) {}
