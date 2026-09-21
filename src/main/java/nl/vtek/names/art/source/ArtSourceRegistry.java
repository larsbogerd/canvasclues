package nl.vtek.names.art.source;

import nl.vtek.names.art.model.Artwork;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArtSourceRegistry {

    private final Map<String, ArtSource> sources;

    public ArtSourceRegistry(List<ArtSource> artSources) {
        this.sources = artSources.stream()
                .collect(Collectors.toMap(ArtSource::key, source -> source));
    }

    public ArtSource get(String key) {
        ArtSource source = sources.get(key);
        if (source == null) {
            throw new IllegalStateException("No art source registered under key: " + key);
        }
        return source;
    }

    public String imageUrlTemplate(Artwork artwork) {
        return get(artwork.getSource()).imageUrlTemplate(artwork.getExternalImageId());
    }
}
