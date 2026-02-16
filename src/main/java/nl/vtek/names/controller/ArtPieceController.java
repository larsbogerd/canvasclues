package nl.vtek.names.controller;

import nl.vtek.names.model.ArtPiece;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/artpieces")
public class ArtPieceController {

    private final ArtPieceService artPieceService;
    private final ArtPieceRepo artPieceRepo;

    public ArtPieceController (ArtPieceService artPieceService, ArtPieceRepo artPieceRepo) {
        this.artPieceService = artPieceService;
        this.artPieceRepo = artPieceRepo;
    }

    @GetMapping
    public List<ArtPiece> getArtPieces() {
        return artPieceService.getAllArtPieces();
    };

    @PostMapping
    public void addArtPiece(@RequestBody ArtPiece artPiece) {
        ArtPieceService.insertArtPiece(artPiece);
        artPieceRepo.save(artPiece);
    }





}
