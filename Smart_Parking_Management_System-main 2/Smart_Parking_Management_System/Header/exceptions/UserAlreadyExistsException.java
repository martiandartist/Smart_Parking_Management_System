package Header.exceptions;
public class UserAlreadyExistsException extends Exception {
    //Exception calss which is thrown when a user tries to register with an already existing user email
    public static final String DEFAULT_MESSAGE = "User already exists!";

    public UserAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}