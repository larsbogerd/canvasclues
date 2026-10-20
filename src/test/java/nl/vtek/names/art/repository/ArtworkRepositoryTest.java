package nl.vtek.names.art.repository;

import nl.vtek.names.art.model.Artwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ArtworkRepositoryTest {

    @Autowired
    private ArtworkRepository artworkRepository;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 20; i++) {
            artworkRepository.save(artwork("met", "met-" + i));
        }
        for (int i = 0; i < 5; i++) {
            artworkRepository.save(artwork("cma", "cma-" + i));
        }
    }

    @Test
    void findBySourceAndExternalImageId_isScopedToTheSource() {
        assertThat(artworkRepository.findBySourceAndExternalImageId("met", "met-3")).isPresent();
        assertThat(artworkRepository.findBySourceAndExternalImageId("cma", "met-3")).isEmpty();
    }

    private static Artwork artwork(String source, String imageId) {
        return new Artwork(source, imageId, "Title " + imageId, "Artist", null, null, null, null, null, null,
                null, null);
    }
}
