package be.howest.ti.stratego2021.logic.exceptions;

/**
 * StrategoGameRuleException is a runtime exception that
 * can be thrown when you detect a violation of one of the game rules.
 *
 * For example, if you detect that a player tries to move a pieces of
 * his opponent.
 *
 * Make sure your game did not change (e.g., revert) before throwing.
 * The default behavior is to respond with a 409 HTTP code.
 */
public class StrategoGameRuleException extends RuntimeException {

    public static final long serialVersionUID = 10000;

    public StrategoGameRuleException(String msg) {
        super(msg);
    }
}
