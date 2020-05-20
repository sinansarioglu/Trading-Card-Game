import java.util.NoSuchElementException;
import java.util.Scanner;
import Exception.*;

public class GameEngine {
    private boolean isSurrendered;
    public static boolean isInvalidCommand = false;
    public void play(Scanner scanner, String playerName1, String playerName2, int[] cardArray, int health, int initialCardCountOnHand) {
        Player player1 = new Player(playerName1);
        Player player2 = new Player(playerName2);
        setSurrendered(false);
        Environment environment = new Environment();
        environment.setup(player1, player2, health, initialCardCountOnHand, cardArray);
        Moderator moderator = new Moderator(player1, player2);
        System.out.println("Game is starting...");
        System.out.println("Toss-Coin is being done to decide who is starting...");
        try {
            while (true) {
                //Necessary updates are held at start of each turn
                if(!moderator.onTurnStart(moderator.getAttacker())) {
                    System.out.println(moderator.getOpponent().getName() + " won the match!");
                }
                //Player can attack multiple times in a turn
                while(true) {
                    System.out.println("******* " + moderator.getAttacker().getName() + "'s turn *******");
                    System.out.println("Remaining mana to use in this round: " + moderator.getAttacker().getRemainingMana());
                    System.out.println("The opponent " + moderator.getOpponent().getName() + "'s remaining health: " + moderator.getOpponent().getHealth());
                    if(moderator.getAttacker().getDamageCardsOnHand().size()>0){
                        System.out.println(moderator.getAttacker().getName() + " has following damage card(s) to attack:");
                        moderator.getAttacker().showDamageCardsOnHand();
                        System.out.println("Type damage card index in order to attack or type 'pass' to end your turn.");
                    } else {
                        System.out.println("There is no card to attack. Type 'pass' to continue.");
                    }

                    String line = scanner.nextLine().trim().toLowerCase();
                    if(line.equals("pass")) {
                        moderator.changeAttacker();
                        break;
                    }
                    else if(line.equals("surrender")) {
                        System.out.println(moderator.getOpponent().getName() + " won the game!");
                        setSurrendered(true);
                        return;
                    }
                    else {
                        try {
                            int index = Integer.parseInt(line);
                            //Attack with a valid DamageCard
                            if(!moderator.attack(moderator.getAttacker(),moderator.getOpponent(),index)) {
                                System.out.println(moderator.getAttacker().getName() + " won the match!");
                                return;
                            }
                        } catch (NumberFormatException ex) {
                            System.err.println("Illegal command. Please enter valid command!");
                            GameEngine.isInvalidCommand = true;
                        } catch (InvalidCardException | EnoughManaException ex) {

                        }
                    }
                }
            }
        } catch(IllegalStateException | NoSuchElementException e) {
            System.err.println("No input is found. Exiting...");
        }
    }

    public boolean isSurrendered() {
        return isSurrendered;
    }

    public void setSurrendered(boolean surrendered) {
        isSurrendered = surrendered;
    }
}
