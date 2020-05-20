import java.util.Random;
import Exception.*;

public class Moderator {
    private boolean isPlayer1Turn;
    private Player attacker = null, opponent = null;
    private final int maxCardCountOnHand = 5;

    public Moderator(Player player1, Player player2) {
        this.isPlayer1Turn = new Random().nextBoolean();
        this.attacker = this.isPlayer1Turn ? player1 : player2;
        this.opponent = this.isPlayer1Turn ? player2 : player1;
    }

    public boolean onTurnStart(Player attacker) {
        attacker.updateManaSlotAndRefreshRemainingMana();
        if(attacker.getDamageCardDeck().size() > 0) {
            if(attacker.getDamageCardsOnHand().size() >= maxCardCountOnHand) {
                attacker.getRandomCard();
            } else {
                attacker.getDamageCardsOnHand().add(attacker.getRandomCard());
            }
        } else {
            attacker.setHealth(attacker.getHealth()-1);
            if(attacker.getHealth() <= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean attack(Player attacker, Player opponent, int cardIndex) {
        if(cardIndex >= 0 && cardIndex < attacker.getDamageCardsOnHand().size()) {
            DamageCard damageCard = attacker.getDamageCardsOnHand().get(cardIndex);
            if(attacker.getRemainingMana() >= damageCard.getManaCost()) {
                attacker.getDamageCardsOnHand().remove(damageCard);
                attacker.setRemainingMana(attacker.getRemainingMana()-damageCard.getManaCost());
                int opponentHealth = opponent.getHealth()-damageCard.getManaCost();
                System.out.println("You damaged " + damageCard.getManaCost() + " health unit of " + opponent.getName());
                if(opponentHealth <= 0)
                    return false;
                opponent.setHealth(opponentHealth);
            } else {
                throw new EnoughManaException();
            }
        } else {
            throw new InvalidCardException();
        }
        return true;
    }

    public void changeAttacker() {
        setPlayer1Turn(!isPlayer1Turn());
        Player temp = getAttacker();
        setAttacker(getOpponent());
        setOpponent(temp);
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        isPlayer1Turn = player1Turn;
    }

    public Player getAttacker() {
        return attacker;
    }

    public void setAttacker(Player attacker) {
        this.attacker = attacker;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
}
