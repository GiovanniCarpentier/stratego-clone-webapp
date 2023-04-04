package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.web.tokens.PlainTextTokens;
import be.howest.ti.stratego2021.web.tokens.TokenManager;
import io.vertx.core.json.JsonArray;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PlayGame {

    private int gameNumber = 0;

    private final TokenManager tokenManager = new PlainTextTokens();

    //id zonder nummer
    private final static HashMap<String, Game> gamesLookingForPlayer = new HashMap<>();

    //id met nummer
     private final static HashMap<String, Game> gamesInProgress = new HashMap<>();

    public Map<String, String> connectPlayerWithGame(JsonArray board, String version, String roomId, String side){
        Game game;
        String gameId;
        //new game
        if(side.equalsIgnoreCase("red")){
            game = new Game(version, this.gameNumber);
            gameId = roomId+gameNumber;
            gameNumber++;

        //existing game
        }else{
            game = getGameLookingForPlayer(roomId);
            int number = game.getGameNumber();
            gameId = roomId+number;
        }

        String playerToken = tokenManager.createToken(gameId, side);
        game.addPlayer(playerToken, board);

        if(side.equalsIgnoreCase("red")){
            gamesLookingForPlayer.put(roomId, game);
        } else {
            gamesLookingForPlayer.remove(roomId);
        }

        gamesInProgress.put(gameId, game);
        Map<String, String> gameData = new HashMap<>();
        gameData.put("gameId",gameId);
        gameData.put("player",side.toLowerCase(Locale.ROOT));
        gameData.put("playerToken",playerToken);

        game.emptyMove(side);

        return gameData;
    }

    public Map<String, String> startGame(JsonArray board, String version, String roomId){
        Game room = getGameLookingForPlayer(roomId);
        String side;
        Map<String, String> gameData;
        if(room == null){
            side = "RED";
        }else {
            side = "BLUE";
        }
        gameData = connectPlayerWithGame(board, version, roomId, side);
        return gameData;
    }

    public static String getInversePlayerColor(String player){
        if(player.equalsIgnoreCase("red")){
            return "blue";
        }else{
            return "red";
        }
    }


    //id zonder nummer
    public static Game getGameLookingForPlayer(String roomId){
        return gamesLookingForPlayer.getOrDefault(roomId, null);
    }

    //id met nummer
    public static Game getGameInProgress(String gameId){
        return gamesInProgress.getOrDefault(gameId, null);
    }
}
