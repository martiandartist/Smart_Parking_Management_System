package Header.exceptions;
public class InvalidInputException extends Exception {
    // Exception class which is thrown when the user provides invalid input
    public static final String DEFAULT_MESSAGE = "Invalid input provided!";

    public InvalidInputException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidInputException(String message) {
        super(message);
    }
    
}
