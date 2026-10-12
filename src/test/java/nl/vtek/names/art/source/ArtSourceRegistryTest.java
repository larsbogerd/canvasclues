package nl.vtek.names.art.source;

import nl.vtek.names.art.client.ArticClient;
import nl.vtek.names.art.client.CmaClient;
import nl.vtek.names.art.client.MetClient;
import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.game.exception.UnknownArtSourceException;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArtSourceRegistryTest {

    private final ArtSourceRegistry registry = new ArtSourceRegistry(List.of(
            new ArticClient(RestClient.builder()),
            new CmaClient(RestClient.builder()),
            new MetClient(RestClient.builder())
    ), "cma");

    @Test
    void resolve_withoutARequest_usesTheConfiguredDefault() {
        assertThat(registry.resolve(null).key()).isEqualTo("cma");
        assertThat(registry.resolve(" ").key()).isEqualTo("cma");
        assertThat(registry.resolve("artic").key()).isEqualTo("artic");
    }

    @Test
    void resolve_unknownKey_isABadRequest() {
        assertThatThrownBy(() -> registry.resolve("rijksmuseum"))
                .isInstanceOf(UnknownArtSourceException.class)
                .hasMessage("Unknown art source: rijksmuseum");
    }

    @Test
    void imageUrlTemplate_iiifSourceGetsASizePlaceholder() {
        assertThat(registry.imageUrlTemplate(artwork("artic", "abc-123")))
                .isEqualTo("/img/artic/abc-123/full/{size}/0/default.jpg");
    }

    @Test
    void imageUrlTemplate_fixedDerivativeSourcesAreServedAsIs() {
        assertThat(registry.imageUrlTemplate(artwork("cma", "1916.1/1916.1_web.jpg")))
                .isEqualTo("/img/cma/1916.1/1916.1_web.jpg");
        assertThat(registry.imageUrlTemplate(artwork("met", "CRDImages/ep/web-large/DT1.jpg")))
                .isEqualTo("/img/met/CRDImages/ep/web-large/DT1.jpg");
    }

    private static Artwork artwork(String source, String imageId) {
        Artwork artwork = new Artwork();
        artwork.setSource(source);
        artwork.setExternalImageId(imageId);
        return artwork;
    }
}
