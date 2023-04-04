package be.howest.ti.stratego2021.web.exceptions;

/**
 * ForbiddenAccessException is a runtime exception that
 * can be thrown when you realize that the authorized game-id and
 * authorized player do not have sufficient rights to execute
 * the request.
 *
 * This exception should be thrown only in the web-package (and its
 * subpackages).
 *
 * Make sure your game did not change (e.g., revert) before throwing.
 * The default behavior is to respond with a 403 HTTP code.
 */
public class ForbiddenAccessException extends RuntimeException {
    public static final long serialVersionUID = 10000;
}
