package nl.vtek.names.art.service;

import nl.vtek.names.art.client.ArticClient;
import nl.vtek.names.art.dto.ArtWorkResponse;
import nl.vtek.names.art.mapper.ArtWorkMapper;
import nl.vtek.names.art.model.ArtWork;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ArtWorkService {

    private final ArticClient articClient;
    private final ArtWorkMapper artWorkMapper;

    public ArtWorkService(ArticClient articClient, ArtWorkMapper artWorkMapper) {
        this.articClient = articClient;
        this.artWorkMapper = artWorkMapper;
    }

    public List<ArtWorkResponse> searchArtworks(int size) {
        List<ArtWork> artworks = new ArrayList<>(
                articClient.searchArtworks(100).pulledData().stream()
                        .map(artWorkMapper::toEntity)
                        .filter(artWork -> artWork.getImageId() != null)
                        .toList()
        );

        Collections.shuffle(artworks);

        List<ArtWorkResponse> responses = new ArrayList<>();
        for (int i = 0; i < Math.min(size, artworks.size()); i++) {
            responses.add(artWorkMapper.toResponse(artworks.get(i), i + 1));
        }
        return responses;
    }
}
