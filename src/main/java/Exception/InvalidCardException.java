package Exception;

public class InvalidCardException extends RuntimeException {
    public InvalidCardException() {
        System.err.println("There is no such a card!");
    }
}
