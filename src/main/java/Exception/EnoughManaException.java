package Exception;

public class EnoughManaException extends RuntimeException {
    public EnoughManaException() {
        System.err.println("You do not have enough mana to use this card!");
    }
}
