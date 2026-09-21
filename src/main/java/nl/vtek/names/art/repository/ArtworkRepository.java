package nl.vtek.names.art.repository;

import nl.vtek.names.art.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtworkRepository extends JpaRepository<Artwork, UUID> {

    List<Artwork> findByFirstUsedAtNotNull();

    Optional<Artwork> findBySourceAndExternalImageId(String source, String externalImageId);
}
