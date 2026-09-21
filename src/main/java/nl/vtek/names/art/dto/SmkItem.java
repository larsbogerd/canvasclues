package nl.vtek.names.art.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SmkItem(
        @JsonProperty("image_iiif_id") String imageIiifId,
        List<Title> titles,
        List<String> artist,
        @JsonProperty("production_date") List<ProductionDate> productionDate,
        List<String> techniques,
        List<Dimension> dimensions,
        @JsonProperty("responsible_department") String responsibleDepartment,
        @JsonProperty("content_description") List<String> contentDescription
) {

    public record Title(String title) {}

    public record ProductionDate(String period) {}

    /** {@code notes} is pre-formatted by SMK, e.g. "370 x 535 mm". */
    public record Dimension(String notes) {}
}
