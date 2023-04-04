package be.howest.ti.stratego2021.web.tokens;

import io.vertx.ext.auth.User;

/**
 * A TokenManager is responsible for
 *  - creating/computing (new) tokens based on a game-id and a player (name), and
 *  - should be able to lookup/compute the game-id and the player for a given token.
 *
 *  If a token was successfully parsed, the game-id and player can be accessed from a
 *  StrategoRequestParameters-object using the getAuthorizedGameId and getAuthorizedPlayer
 *  methods. If you fail to parse a token you should throw an InvalidTokenException.
 *
 */
public interface TokenManager {

    String createToken(String gameId, String player);

    String token2gameId(String token);
    String token2player(String token);

    default User createUser(String token) {
        User user = User.fromToken(token);
        user.principal()
                .put("gameId", token2gameId(token))
                .put("player", token2player(token));
        return user;
    }

}
