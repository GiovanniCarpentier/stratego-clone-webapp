package be.howest.ti.stratego2021.web.bridge;

import be.howest.ti.stratego2021.logic.*;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * The StrategoRequestParameters class is responsible for translating information that is part of the
 * request into Java.
 */
public class StrategoRequestParameters {

    public static StrategoRequestParameters from(RoutingContext ctx) {
        return new StrategoRequestParameters(ctx);
    }

    private final RequestParameters params;
    private final User user;

    private StrategoRequestParameters(RoutingContext ctx) {
        this.params = ctx.get(ValidationHandler.REQUEST_CONTEXT_KEY);
        this.user = ctx.user();
    }

    public String getFilterForVersion() {
        return params.pathParameter("version").getString(); // from path
    }

    public String getAuthorizedGameId() {
        return user.get("gameId");
    }

    public String getAuthorizedPlayer() {
        return user.get("player");
    }
    public String getPlayerToken() { return  getAuthorizedGameId()+"-"+getAuthorizedPlayer(); }


    public Map<String, String> getConfig() {
        String roomId = params.pathParameter("roomId").getString();
        JsonObject body = params.body().getJsonObject();
        String version = body.getString("version");
        JsonArray board = body.getJsonArray("startConfiguration");

        Map<String, String> output = new HashMap<>();
        output.put("version", version);
        output.put("board", board.toString());
        output.put("roomId", roomId);

        return output;
    }

    public Moves getMovePlayerDid() {
        JsonObject body = params.body().getJsonObject();
        return new Moves(getAuthorizedPlayer(), null, null, null, body.getJsonObject("src"), body.getJsonObject("tar"));
    }


    public String getGameId() {
        return params.pathParameter("gameId").getString();
    }

}


