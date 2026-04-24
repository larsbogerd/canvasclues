package nl.vtek.names.art.repository;

import nl.vtek.names.art.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtworkRepository extends JpaRepository<Artwork, UUID> {

}
