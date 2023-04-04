package be.howest.ti.stratego2021.web.tokens;

import be.howest.ti.stratego2021.web.exceptions.InvalidTokenException;

public class PlainTextTokens implements TokenManager {

    @Override
    public String createToken(String gameId, String player) {
        return gameId + "-" + player;
    }

    @Override
    public String token2gameId(String token) {
        return parseToken(token, 0);
    }

    @Override
    public String token2player(String token) {
        return parseToken(token, 1);
    }

    private String parseToken(String token, int part) {
        String[] parts = token.split("-");

        if (parts.length != 2) {
            throw new InvalidTokenException();
        }
        return parts[part];
    }

}
