package nl.vtek.names.art.service;

import nl.vtek.names.art.source.ArtSource;
import nl.vtek.names.art.source.ArtSourceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@ConditionalOnProperty(name = "art.pool.enabled", havingValue = "true", matchIfMissing = true)
public class ArtworkPoolRefiller {

    private static final Logger log = LoggerFactory.getLogger(ArtworkPoolRefiller.class);

    private final ArtSourceRegistry artSources;
    private final ArtworkService artworkService;
    private final int targetSize;

    public ArtworkPoolRefiller(ArtSourceRegistry artSources,
                               ArtworkService artworkService,
                               @Value("${art.pool.target-size}") int targetSize) {
        this.artSources = artSources;
        this.artworkService = artworkService;
        this.targetSize = targetSize;
    }

    @Scheduled(fixedDelayString = "${art.pool.refill-delay}")
    public void refill() {
        int batchSize = 16;
        for (ArtSource source : artSources.all()) {
            try {
                int added = artworkService.topUpPool(source, targetSize, batchSize);
                if (added > 0) {
                    log.info("Added {} artworks to the {} pool", added, source.key());
                }
            } catch (RuntimeException e) {
                log.warn("Refilling the {} pool failed: {}", source.key(), e.getMessage());
            }
        }
    }
}
