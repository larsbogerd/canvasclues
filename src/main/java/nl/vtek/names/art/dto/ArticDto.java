package nl.vtek.names.art.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArticDto(
        int id,
        String title,
        @JsonProperty("artist_display") String artistDisplay,
        @JsonProperty("image_id") String imageId,
        @JsonProperty("date_display") String dateDisplay,
        @JsonProperty("medium_display") String mediumDisplay,
        @JsonProperty("place_of_origin") String placeOfOrigin
) {}
