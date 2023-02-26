package by.dudko.questionnaires.exception;

public class UserNotFoundException extends RuntimeException {
    public static UserNotFoundException of(String email) {
        return new UserNotFoundException("User with email: " + email + " not found");
    }

    public static UserNotFoundException of(long userId) {
        return new UserNotFoundException("User with id: " + userId + " not found");
    }

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
