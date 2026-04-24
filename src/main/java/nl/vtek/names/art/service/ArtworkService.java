package nl.vtek.names.art.service;

import nl.vtek.names.art.client.ArticClient;
import nl.vtek.names.art.dto.ArtworkResponse;
import nl.vtek.names.art.dto.ArtworkStatsListResponse;
import nl.vtek.names.art.dto.ArtworkStatsResponse;
import nl.vtek.names.art.mapper.ArtworkMapper;
import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.art.repository.ArtworkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ArtworkService {

    private final ArticClient articClient;
    private final ArtworkMapper artworkMapper;
    private final ArtworkRepository artworkRepository;

    public ArtworkService(ArticClient articClient, ArtworkMapper artworkMapper, ArtworkRepository artworkRepository) {
        this.articClient = articClient;
        this.artworkMapper = artworkMapper;
        this.artworkRepository = artworkRepository;
    }

    public List<Artwork> fetchAndSaveArtworks(int size) {
        List<Artwork> artworks = new ArrayList<>(
                articClient.searchArtworks(size + 4).pulledData().stream()
                        .map(artworkMapper::toEntity)
                        .filter(artwork -> artwork.getId() != null)
                        .map(artwork ->
                                artworkRepository.findById(artwork.getId()).orElseGet(() ->
                                        artworkRepository.save(artwork)))
                        .limit(size)
                        .toList()
        );

        Collections.shuffle(artworks);
        return artworks;
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
