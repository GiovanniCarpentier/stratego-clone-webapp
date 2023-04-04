package be.howest.ti.stratego2021.web.bridge;

import be.howest.ti.stratego2021.logic.Location;
import be.howest.ti.stratego2021.logic.Moves;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;


/**
 * The StrategoResponses class is responsible for translating the result of the controller into
 * JSON responses with an appropriate HTTP code.
 */
public class StrategoResponses {

    private StrategoResponses() { /* utility class */ }

    private static void sendJsonResponse(RoutingContext ctx, int statusCode, Object response) {
        ctx.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setStatusCode(statusCode)
                .end(Json.encodePrettily(response));
    }


    public static void sendStrategoVersions(RoutingContext ctx, List<String> versions) {

        sendJsonResponse(ctx, 200, new JsonObject().put("versions", versions));
    }

    public static void sendStrategoVersion(RoutingContext ctx, String version, JsonObject body ){
        sendJsonResponse(ctx, 200, new JsonObject()
                .put("name", version)
                .put("pieceCount",body));
    }

    public static void sendMove(RoutingContext ctx, Moves move, String player) {
        if(move != null) {
            sendJsonResponse(ctx, 200, new JsonObject()
                    .put("player", player)
                    .put("src", move.getSrc())
                    .put("tar", move.getTar()));
        }else{
            sendFailure(ctx, 400, "Deze move gaat niet!");
        }
    }

    public static void sendMoves(RoutingContext ctx, List<Moves> movesList) {
        sendJsonResponse(ctx, 200, new JsonObject()
                .put("moves", movesList));
    }

    public static void sendGameState(RoutingContext ctx, Location[][] boardLayout) {
        sendJsonResponse(ctx, 200, new JsonObject()
                .put("configuration", boardLayout));
    }

    public static void sendJoinedGameInfo(RoutingContext ctx, String gameId, String player, String playerToken ) {
        sendJsonResponse(ctx, 200, new JsonObject()
                .put("gameId", gameId)
                .put("player",player)
                .put("playerToken",playerToken));
    }

    public static void sendFailure(RoutingContext ctx, int code, String message) {
        sendJsonResponse(ctx, code, new JsonObject()
                .put("failure", code)
                .put("cause", message));
    }


}
