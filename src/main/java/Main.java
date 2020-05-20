import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String playerName1 = "Player1", playerName2 = "Player2";
        int[] cardArray = new int[]{0,0,1,1,2,2,2,3,3,3,3,4,4,4,5,5,6,6,7,8};
        int health = 30;
        int initialCardCountOnHand = 3;
        GameEngine gameEngine = new GameEngine();
        gameEngine.play(scanner,playerName1,playerName2,cardArray,health,initialCardCountOnHand);
    }
}
