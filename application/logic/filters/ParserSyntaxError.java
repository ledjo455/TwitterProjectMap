package application.logic.filters;

/**
 * The exception thrown when parsing a string fails.
 * @parameter message is the message for the exception
 */
public class ParserSyntaxError extends Exception {
    public ParserSyntaxError(String message) {
        super(message);
    }
}
