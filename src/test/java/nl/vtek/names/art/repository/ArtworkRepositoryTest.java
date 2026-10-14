package nl.vtek.names.art.repository;

import nl.vtek.names.art.model.Artwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

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
    void findRandomBySource_drawsDistinctArtworksFromOneSource() {
        List<Artwork> drawn = artworkRepository.findRandomBySource("met", 16);

        assertThat(drawn).hasSize(16).doesNotHaveDuplicates().allMatch(artwork -> "met".equals(artwork.getSource()));
    }

    @Test
    void findRandomBySource_returnsWhatThereIsWhenThePoolIsSmall() {
        assertThat(artworkRepository.findRandomBySource("cma", 16)).hasSize(5);
        assertThat(artworkRepository.findRandomBySource("artic", 16)).isEmpty();
    }

    @Test
    void findRandomBySource_shufflesBetweenDraws() {
        List<String> first = imageIds(artworkRepository.findRandomBySource("met", 20));

        boolean reordered = false;
        for (int i = 0; i < 10 && !reordered; i++) {
            reordered = !first.equals(imageIds(artworkRepository.findRandomBySource("met", 20)));
        }

        assertThat(reordered).isTrue();
    }

    @Test
    void countBySource_countsOneSource() {
        assertThat(artworkRepository.countBySource("met")).isEqualTo(20);
        assertThat(artworkRepository.countBySource("artic")).isZero();
    }

    @Test
    void findBySourceAndExternalImageId_isScopedToTheSource() {
        assertThat(artworkRepository.findBySourceAndExternalImageId("met", "met-3")).isPresent();
        assertThat(artworkRepository.findBySourceAndExternalImageId("cma", "met-3")).isEmpty();
    }

    private static List<String> imageIds(List<Artwork> artworks) {
        return artworks.stream().map(Artwork::getExternalImageId).toList();
    }

    private static Artwork artwork(String source, String imageId) {
        return new Artwork(source, imageId, "Title " + imageId, "Artist", null, null, null, null, null, null,
                null, null);
    }
}
