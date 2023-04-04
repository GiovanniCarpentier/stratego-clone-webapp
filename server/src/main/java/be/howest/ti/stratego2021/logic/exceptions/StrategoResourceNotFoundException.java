package be.howest.ti.stratego2021.logic.exceptions;

/**
 * StrategoResourceNotFoundException is a runtime exception that
 * can be thrown when a request is made but one (or more) of the elements needed
 * for the requested computation cannot be found (e.g., a version, a game, a player, ...)
 *
 * Make sure your games did not change (e.g., revert) before throwing.
 * The default behavior is to respond with a 404 HTTP code.
 */
public class StrategoResourceNotFoundException extends RuntimeException {

    public static final long serialVersionUID = 10000;

    public StrategoResourceNotFoundException(String msg) {
        super(msg);
    }

}
