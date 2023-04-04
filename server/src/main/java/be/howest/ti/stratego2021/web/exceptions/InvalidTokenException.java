package be.howest.ti.stratego2021.web.exceptions;

/**
 * InvalidTokenException is a runtime exception that
 * can be thrown when you fail to parse a player token
 * into an game-id and a player name. Usually this exception
 *
 * Usually this exception is only thrown in implementations of the
 * TokenManager interface.
 *
 * Make sure your game did not change (e.g., revert) before throwing.
 * The default behavior is to respond with a 403 HTTP code.
 */
public class InvalidTokenException extends RuntimeException {
    public static final long serialVersionUID = 10000;
}
