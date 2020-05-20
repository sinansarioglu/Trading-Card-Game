import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private String name;
    private List<DamageCard> damageCardsOnHand = new ArrayList<>();
    private List<DamageCard> damageCardDeck = new ArrayList<>();
    private int health;
    private int manaSlot = 0;
    private int remainingMana;
    private int maximumManaSlot = 10;
    private Random random = new Random();

    public Player(String name) {
        this.name = name;
    }

    public List<DamageCard> getDamageCardsOnHand() {
        return damageCardsOnHand;
    }

    public void setDamageCardsOnHand(List<DamageCard> damageCardsOnHand) {
        this.damageCardsOnHand = damageCardsOnHand;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getManaSlot() {
        return manaSlot;
    }

    public void setManaSlot(int manaSlot) {
        this.manaSlot = manaSlot;
    }

    public List<DamageCard> getDamageCardDeck() {
        return damageCardDeck;
    }

    public void setDamageCardDeck(List<DamageCard> damageCardDeck) {
        this.damageCardDeck = damageCardDeck;
    }

    public DamageCard getRandomCard() {
        //Get a random DamageCard from the player's DamageCard deck
        DamageCard card = damageCardDeck.get(random.nextInt(damageCardDeck.size()));
        damageCardDeck.remove(card);
        return card;
    }

    public void updateManaSlotAndRefreshRemainingMana() {
        //Update manaSlot as long as it does not reach to maximumManaSlot count
        if(this.manaSlot < this.maximumManaSlot)
            this.manaSlot++;
        //Update remainig mana of the player
        setRemainingMana(getManaSlot());
    }

    public int getRemainingMana() {
        return remainingMana;
    }

    public void setRemainingMana(int remainingMana) {
        this.remainingMana = remainingMana;
    }

    public void showDamageCardsOnHand() {
        for(int i=0; i<damageCardsOnHand.size(); i++) {
            System.out.println("Card index: " + i + " - Mana cost: " + damageCardsOnHand.get(i).getManaCost());
        }
    }

    public String getName() {
        return name;
    }
}
