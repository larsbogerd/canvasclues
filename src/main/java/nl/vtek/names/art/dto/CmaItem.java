package nl.vtek.names.art.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CmaItem(
        @JsonProperty("accession_number") String accessionNumber,
        String title,
        @JsonProperty("creation_date") String creationDate,
        String technique,
        String department,
        List<String> culture,
        String measurements,
        String description,
        List<Creator> creators,
        Images images
) {

    public record Creator(String description) {}

    public record Images(Web web) {}

    public record Web(String url, String width, String filename) {}
}
