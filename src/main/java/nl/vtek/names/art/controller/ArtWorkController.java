package nl.vtek.names.art.controller;

import nl.vtek.names.art.dto.ArtWorkResponse;
import nl.vtek.names.art.model.ArtWork;
import nl.vtek.names.art.service.ArtWorkService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/artworks")
public class ArtWorkController {

    private final ArtWorkService artWorkService;

    public ArtWorkController(ArtWorkService artWorkService) {
        this.artWorkService = artWorkService;
    }

    @GetMapping
    public List<ArtWork> getArtworks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "25") int limit) {
        return artWorkService.fetchArtworks(page, limit);
    }

    @GetMapping("/random")
    public List<ArtWorkResponse> getRandomArtworks() {
        return artWorkService.getRandomArtworks();
    }
}
