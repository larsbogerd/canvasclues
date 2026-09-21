package nl.vtek.names.art.dto;

import java.util.List;

public record SmkResponse(int found, List<SmkItem> items) {}
