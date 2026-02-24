package nl.vtek.names.helloworld.service;

import nl.vtek.names.helloworld.repository.ArtPieceRepo;
import nl.vtek.names.helloworld.model.ArtPiece;
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


