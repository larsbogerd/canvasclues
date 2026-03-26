package nl.vtek.names.art.controller;

import nl.vtek.names.art.dto.ArtWorkResponse;
import nl.vtek.names.art.service.ArtWorkService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/artworks")
public class ArtWorkController {

    private final ArtWorkService artWorkService;

    public ArtWorkController(ArtWorkService artWorkService) {
        this.artWorkService = artWorkService;
    }

    @GetMapping("/test")
    public List<ArtWorkResponse> getArtworks(
            @RequestParam(defaultValue = "25") int size) {
        return artWorkService.searchArtworks(size);
    }
}
