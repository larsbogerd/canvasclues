package nl.vtek.names.controller;

import nl.vtek.names.model.ArtPiece;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtPieceRepo extends JpaRepository<ArtPiece, Long> {



}
