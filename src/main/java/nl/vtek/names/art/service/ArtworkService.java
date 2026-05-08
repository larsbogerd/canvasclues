package nl.vtek.names.art.service;

import nl.vtek.names.art.client.ArticClient;
import nl.vtek.names.art.dto.ArtworkResponse;
import nl.vtek.names.art.dto.ArtworkStatsListResponse;
import nl.vtek.names.art.dto.ArtworkStatsResponse;
import nl.vtek.names.art.mapper.ArtworkMapper;
import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.repository.ArtworkRepository;
import nl.vtek.names.game.exception.GameNotFoundException;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ArtworkService {

    private final ArticClient articClient;
    private final ArtworkMapper artworkMapper;
    private final ArtworkRepository artworkRepository;
    private final GameRepository gameRepository;

    public ArtworkService(ArticClient articClient,
                          ArtworkMapper artworkMapper,
                          ArtworkRepository artworkRepository,
                          GameRepository gameRepository) {
        this.articClient = articClient;
        this.artworkMapper = artworkMapper;
        this.artworkRepository = artworkRepository;
        this.gameRepository = gameRepository;
    }

    public void recordArtworkUsage(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        LocalDateTime now = LocalDateTime.now();
        List<Artwork> toUpdate = new ArrayList<>();

        for (Card card : game.getCards()) {
            Artwork artwork = card.getArtwork();
            artwork.setTimesLoaded(artwork.getTimesLoaded() + 1);

            if (artwork.getFirstUsedAt() == null) {
                artwork.setFirstUsedAt(now);
            }
            artwork.setLastUsedAt(now);

            if (card.isSpymasterPick()) {
                artwork.setTimesSpymasterPick(artwork.getTimesSpymasterPick() + 1);
            }
            toUpdate.add(artwork);
        }
        artworkRepository.saveAll(toUpdate);
    }

    public List<Artwork> fetchAndSaveArtworks(int size) {
        List<Artwork> fetched = fetchArtworks(size + 4);
        List<Artwork> saved = new ArrayList<>(sendArtworksToDatabase(fetched, size));
        Collections.shuffle(saved);
        return saved;
    }

    private List<Artwork> fetchArtworks(int size) {
        return articClient.searchArtworks(size).pulledData().stream()
                .map(artworkMapper::toEntity)
                .filter(artwork -> artwork.getId() != null)
                .toList();
    }

    private List<Artwork> sendArtworksToDatabase(List<Artwork> artworks, int limit) {
        return artworks.stream()
                .map(artwork -> artworkRepository.findById(artwork.getId())
                        .orElseGet(() -> artworkRepository.save(artwork)))
                .limit(limit)
                .toList();
    }

    public List<ArtworkResponse> searchArtworks(int size) {
        return fetchAndSaveArtworks(size).stream()
                .map(artworkMapper::toResponse)
                .toList();
    }

    public List<ArtworkStatsListResponse> getArtworkStatsList() {
        return artworkRepository.findAll().stream()
                .map(artworkMapper::toStatsResponse)
                .toList();
    }

    public ArtworkStatsResponse getArtworkDetail(UUID id) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artwork not found: " + id));
        return artworkMapper.toDetailResponse(artwork);
    }
}
