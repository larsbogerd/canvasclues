package nl.vtek.names.art.source;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.game.exception.UnknownArtSourceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArtSourceRegistry {

    private final Map<String, ArtSource> sources;
    private final String defaultKey;

    public ArtSourceRegistry(List<ArtSource> artSources, @Value("${art.source}") String defaultKey) {
        this.sources = artSources.stream()
                .collect(Collectors.toMap(ArtSource::key, source -> source));
        this.defaultKey = defaultKey;
    }

    public ArtSource resolve(String requestedKey) {
        return get(requestedKey == null || requestedKey.isBlank() ? defaultKey : requestedKey);
    }

    public ArtSource get(String key) {
        ArtSource source = sources.get(key);
        if (source == null) {
            throw new UnknownArtSourceException(key);
        }
        return source;
    }

    public String imageUrlTemplate(Artwork artwork) {
        return get(artwork.getSource()).imageUrlTemplate(artwork.getExternalImageId());
    }
}
