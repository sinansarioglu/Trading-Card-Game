import org.junit.Test;
import Exception.*;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TradingCardGameTest {

    @Test
    public void testInvalidCardException() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        int[] cardArray = new int[]{0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8};
        int health = 30;
        int initialCardCountOnHand = 3;
        Environment environment = new Environment();
        environment.setup(player1, player2, health, initialCardCountOnHand, cardArray);
        Moderator moderator = new Moderator(player1, player2);
        assertThrows(InvalidCardException.class, () -> moderator.attack(moderator.getAttacker(),moderator.getOpponent(),4));
    }

    @Test
    public void testHitDamage() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        player1.setRemainingMana(5);
        player2.setRemainingMana(5);
        int[] cardArray = new int[]{5};
        int health = 10;
        int initialCardCountOnHand = 1;
        Environment environment = new Environment();
        environment.setup(player1, player2, health, initialCardCountOnHand, cardArray);
        Moderator moderator = new Moderator(player1, player2);
        moderator.attack(moderator.getAttacker(), moderator.getOpponent(), 0);
        Assertions.assertFalse(health == moderator.getOpponent().getHealth());
    }

    @Test
    public void testWinCase() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        player1.setRemainingMana(5);
        player2.setRemainingMana(5);
        int[] cardArray = new int[]{5};
        int health = 1;
        int initialCardCountOnHand = 1;
        Environment environment = new Environment();
        environment.setup(player1, player2, health, initialCardCountOnHand, cardArray);
        Moderator moderator = new Moderator(player1, player2);
        Assertions.assertFalse(() -> moderator.attack(moderator.getAttacker(), moderator.getOpponent(), 0));
    }

    @Test
    public void testPass() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        int[] cardArray = new int[]{0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8};
        int health = 30;
        int initialCardCountOnHand = 3;
        Environment environment = new Environment();
        environment.setup(player1, player2, health, initialCardCountOnHand, cardArray);
        Moderator moderator = new Moderator(player1, player2);

        boolean isPlayer1Turn = moderator.isPlayer1Turn();
        moderator.changeAttacker();
        assertEquals(moderator.isPlayer1Turn(), !isPlayer1Turn);
    }

    @Test
    public void testNotEnoughMana() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        Moderator moderator = new Moderator(player1, player2);

        moderator.getAttacker().setManaSlot(2);
        moderator.getAttacker().setRemainingMana(2);
        moderator.getAttacker().setDamageCardsOnHand(Arrays.asList(new DamageCard(3), new DamageCard(4)));

        moderator.getOpponent().setHealth(30);
        assertThrows(EnoughManaException.class, () -> moderator.attack(moderator.getAttacker(), moderator.getOpponent(), 0));

    }

    @Test
    public void testEnoughMana() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        Moderator moderator = new Moderator(player1, player2);

        moderator.getOpponent().setHealth(30);

        List<DamageCard> damageCardList = new ArrayList<>();
        damageCardList.add(new DamageCard(3));
        damageCardList.add(new DamageCard(4));

        moderator.getAttacker().setDamageCardsOnHand(damageCardList);
        moderator.getAttacker().setManaSlot(5);
        moderator.getAttacker().setRemainingMana(5);

        assertEquals(moderator.attack(moderator.getAttacker(), moderator.getOpponent(), 0), true);

    }

    @Test
    public void testSurrender() {
        GameEngine gameEngine = new GameEngine();
        try {
            System.setIn(new ByteArrayInputStream("surrender".getBytes("UTF-8")));
            gameEngine.play(new Scanner(System.in), "Player1", "Player2", new int[]{1,2,3}, 30, 3);
            assertTrue(gameEngine.isSurrendered());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSurrenderWithUpperAndLowerCharacters() {
        GameEngine gameEngine = new GameEngine();
        try {
            System.setIn(new ByteArrayInputStream("SuRReNDeR".getBytes("UTF-8")));
            gameEngine.play(new Scanner(System.in), "Player1", "Player2", new int[]{1,2,3}, 30, 3 );
            assertTrue(gameEngine.isSurrendered());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInvalidCommand() {
        GameEngine gameEngine = new GameEngine();
        try {
            System.setIn(new ByteArrayInputStream("invalidInput".getBytes("UTF-8")));
            while (!GameEngine.isInvalidCommand) {
                gameEngine.play(new Scanner(System.in), "Player1", "Player2", new int[]{1,2,3}, 30, 3);
            }
            assertTrue(GameEngine.isInvalidCommand);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMaximumCardOnHand() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        int initialCardCount = 5;
        int[] cardArray = new int[initialCardCount];
        for(int i=0; i<initialCardCount; i++)
            cardArray[i] = i;
        Environment environment = new Environment();
        player1.setDamageCardsOnHand(environment.createCardList(cardArray));
        player2.setDamageCardsOnHand(environment.createCardList(cardArray));
        Moderator moderator = new Moderator(player1, player2);
        moderator.onTurnStart(moderator.getAttacker());
        Assertions.assertTrue(initialCardCount == moderator.getAttacker().getDamageCardsOnHand().size());
    }

    @Test
    public void testMaximumManaSlot() {
        Player player1 = new Player("Player1");
        int maxManaSlot = 10;
        player1.setManaSlot(maxManaSlot);
        player1.updateManaSlotAndRefreshRemainingMana();
        assertTrue(maxManaSlot == player1.getManaSlot());
    }

    @Test
    public void testIncreseInManaSlot() {
        Player player1 = new Player("Player1");
        int initialManaSlot = 5;
        player1.setManaSlot(initialManaSlot);
        player1.updateManaSlotAndRefreshRemainingMana();
        assertFalse(initialManaSlot == player1.getManaSlot());
    }
}
