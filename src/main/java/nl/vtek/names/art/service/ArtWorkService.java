package nl.vtek.names.art.service;

import nl.vtek.names.art.client.ArticClient;
import nl.vtek.names.art.dto.ArtWorkResponse;
import nl.vtek.names.art.mapper.ArtWorkMapper;
import nl.vtek.names.art.model.ArtWork;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ArtWorkService {

    private final ArticClient articClient;
    private final ArtWorkMapper artWorkMapper;

    public ArtWorkService(ArticClient articClient, ArtWorkMapper artWorkMapper) {
        this.articClient = articClient;
        this.artWorkMapper = artWorkMapper;
    }

    public List<ArtWork> fetchArtworks(int page, int limit) {
        return articClient.getArtworks(page, limit).pulledData().stream()
                .map(artWorkMapper::toEntity)
                .filter(artWork -> artWork.getImageId() != null)
                .toList();
    }

    public List<ArtWorkResponse> getRandomArtworks() {
        List<ArtWork> artworks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int randomPage = ThreadLocalRandom.current().nextInt(1, 999);
            artworks.addAll(fetchArtworks(randomPage, 20));
        }

        Collections.shuffle(artworks);

        List<ArtWorkResponse> responses = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            ArtWork artWork = artworks.get(i);
            responses.add(artWorkMapper.toResponse(artWork, i + 1));
        }
        return responses;
    }
}
