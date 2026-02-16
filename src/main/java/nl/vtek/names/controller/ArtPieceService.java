package nl.vtek.names.controller;

import nl.vtek.names.model.ArtPiece;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtPieceService {

    private final ArtPieceRepo artPieceRepo;


    public ArtPieceService(ArtPieceRepo artPieceRepo) {
        this.artPieceRepo = artPieceRepo;
    }

    public static void insertArtPiece(ArtPiece artPiece) {
    }

    public List<ArtPiece> getAllArtPieces() {
        return artPieceRepo.findAll();
    }

}


