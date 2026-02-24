package nl.vtek.names.helloworld.repository;

import nl.vtek.names.helloworld.model.ArtPiece;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtPieceRepo extends JpaRepository<ArtPiece, Long> {



}
