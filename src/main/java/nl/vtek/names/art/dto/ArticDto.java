package nl.vtek.names.art.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ArticDto(
        @JsonProperty("image_id") UUID id,
        String title,
        @JsonProperty("artist_display") String artistDisplay,
        @JsonProperty("date_display") String dateDisplay,
        @JsonProperty("medium_display") String mediumDisplay,
        @JsonProperty("place_of_origin") String placeOfOrigin,
        String dimensions,
        @JsonProperty("department_title") String departmentTitle
) {}
