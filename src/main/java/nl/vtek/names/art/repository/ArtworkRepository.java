package nl.vtek.names.art.repository;

import nl.vtek.names.art.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtworkRepository extends JpaRepository<Artwork, UUID> {

    List<Artwork> findByFirstUsedAtNotNull();

    Optional<Artwork> findBySourceAndExternalImageId(String source, String externalImageId);

    long countBySource(String source);

    @Query(value = "SELECT * FROM artwork WHERE source = :source ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Artwork> findRandomBySource(String source, int limit);
}
