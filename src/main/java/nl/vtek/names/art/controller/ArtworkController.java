package nl.vtek.names.art.controller;

import nl.vtek.names.art.dto.ArtworkDetailsResponse;
import nl.vtek.names.art.dto.ArtworkResponse;
import nl.vtek.names.art.dto.ArtworkStatsListResponse;
import nl.vtek.names.art.dto.ArtworkStatsResponse;
import nl.vtek.names.art.service.ArtworkService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/artwork")
public class ArtworkController {

    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping("/test")
    public List<ArtworkResponse> getArtworks(
            @RequestParam(defaultValue = "25") int size) {
        return artworkService.searchArtworks(size);
    }

    @GetMapping("/statslist")
    public List<ArtworkStatsListResponse> getArtworkStatsList() {
        return artworkService.getArtworkStatsList();
    }

    @GetMapping("/{id}/stats")
    public ArtworkStatsResponse getArtworkDetail(@PathVariable UUID id) {
        return artworkService.getArtworkDetail(id);
    }

    @GetMapping("/{id}/details")
    public ArtworkDetailsResponse getArtworkDetails(@PathVariable UUID id) {
        return artworkService.getArtworkDetails(id);
    }

    @GetMapping("/details")
    public List<ArtworkDetailsResponse> getArtworkDetailsBatch(@RequestParam List<UUID> ids) {
        return artworkService.getArtworkDetailsBatch(ids);
    }
}
