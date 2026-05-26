package nl.vtek.names.service;

import nl.vtek.names.game.model.CardType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CardServiceTest {

    @Test
    public void giveCardsAType_BoardSize16() {

        int boardSize = 16;

        List<CardType> cardTypes = new ArrayList<>();
        for (int i = 0; i < boardSize / 2; i++) {
            cardTypes.add(CardType.PLAYABLE);
        }
        for (int i = 0; i < boardSize / 2; i++) {
            cardTypes.add(CardType.ASSASSIN);
        }
        Collections.shuffle(cardTypes);

        assertEquals(boardSize, cardTypes.size(), "Total board size should match");
        assertEquals(boardSize / 2, Collections.frequency(cardTypes, CardType.PLAYABLE), "Half the board should be PLAYABLE");
        assertEquals(boardSize / 2, Collections.frequency(cardTypes, CardType.ASSASSIN), "Half the board should be ASSASSIN");
    }

    @Test
    public void giveCardsAType_BoardSize32() {

        int boardSize = 32;

        List<CardType> cardTypes = new ArrayList<>();
        for (int i = 0; i < boardSize / 2; i++) {
            cardTypes.add(CardType.PLAYABLE);
        }
        for (int i = 0; i < boardSize / 2; i++) {
            cardTypes.add(CardType.ASSASSIN);
        }
        Collections.shuffle(cardTypes);

        assertEquals(boardSize, cardTypes.size(), "Total board size should match");
        assertEquals(boardSize / 2, Collections.frequency(cardTypes, CardType.PLAYABLE), "Half the board should be PLAYABLE");
        assertEquals(boardSize / 2, Collections.frequency(cardTypes, CardType.ASSASSIN), "Half the board should be ASSASSIN");
    }
}
