package nl.vtek.names.game.service;

import nl.vtek.names.art.model.Artwork;
import nl.vtek.names.game.exception.CardNotFoundException;
import nl.vtek.names.game.exception.CardNotInSessionGameException;
import nl.vtek.names.game.model.Card;
import nl.vtek.names.game.model.CardType;
import nl.vtek.names.game.model.Game;
import nl.vtek.names.game.repository.CardRepository;
import nl.vtek.names.game.strategy.MediumModeStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @Test
    void buildBoard_dealsOneCardPerArtworkWithTheModesDistribution() {
        Game game = new Game();
        List<Artwork> artworks = IntStream.range(0, 16).mapToObj(i -> new Artwork()).toList();

        List<Card> cards = cardService.buildBoard(game, artworks, new MediumModeStrategy());

        assertThat(cards).hasSize(16).allMatch(card -> card.getGame() == game);
        assertThat(cards).extracting(Card::getArtwork).containsExactlyElementsOf(artworks);
        assertThat(cards).filteredOn(card -> card.getType() == CardType.ASSASSIN).hasSize(6);
        assertThat(cards).noneMatch(Card::isSpymasterPick);
        verify(cardRepository).saveAll(cards);
    }

    @Test
    void updateCards_marksThePickedCards() {
        Card first = new Card(new Game(), CardType.PLAYABLE);
        Card second = new Card(new Game(), CardType.PLAYABLE);
        List<UUID> ids = List.of(UUID.randomUUID(), UUID.randomUUID());
        given(cardRepository.findAllById(ids)).willReturn(List.of(first, second));

        cardService.updateCards(ids, true);

        assertThat(first.isSpymasterPick()).isTrue();
        assertThat(second.isSpymasterPick()).isTrue();
        verify(cardRepository).saveAll(List.of(first, second));
    }

    @Test
    void updateCards_refusesToPickAnAssassin() {
        List<UUID> ids = List.of(UUID.randomUUID());
        given(cardRepository.findAllById(ids)).willReturn(List.of(new Card(new Game(), CardType.ASSASSIN)));

        assertThatThrownBy(() -> cardService.updateCards(ids, true))
                .isInstanceOf(InputMismatchException.class);

        verify(cardRepository, never()).saveAll(any());
    }

    @Test
    void getCardInGame_cardFromAnotherGame_isRejected() {
        Game otherGame = new Game();
        otherGame.setId(2L);
        UUID cardId = UUID.randomUUID();
        given(cardRepository.findById(cardId)).willReturn(Optional.of(new Card(otherGame, CardType.PLAYABLE)));

        assertThatThrownBy(() -> cardService.getCardInGame(cardId, 1L))
                .isInstanceOf(CardNotInSessionGameException.class);
    }

    @Test
    void getCardInGame_unknownCard_throwsNotFound() {
        UUID cardId = UUID.randomUUID();
        given(cardRepository.findById(cardId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> cardService.getCardInGame(cardId, 1L))
                .isInstanceOf(CardNotFoundException.class);
    }
}
