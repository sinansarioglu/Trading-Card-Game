import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Environment {
    public void setup(Player player1, Player player2, int health, int initialCardCountOnHand, int[] cardArray) {
        player1.setDamageCardDeck(createCardList(cardArray));
        setInitialCards(player1, initialCardCountOnHand);
        player1.setHealth(health);

        player2.setDamageCardDeck(createCardList(cardArray));
        setInitialCards(player2, initialCardCountOnHand);
        player2.setHealth(health);
    }

    public List<DamageCard> createCardList(int[] cardArray) {
        return Arrays.stream(cardArray).mapToObj(mana -> new DamageCard(mana)).collect(Collectors.toList());
    }

    private void setInitialCards(Player player, int cardCount) {
        for(int i=0; i<cardCount; i++) {
            player.getDamageCardsOnHand().add(player.getRandomCard());
        }
    }
}
