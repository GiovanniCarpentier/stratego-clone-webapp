package be.howest.ti.stratego2021.logic;

import be.howest.ti.stratego2021.web.StrategoWebController;
import io.vertx.core.json.JsonArray;

import java.util.*;

/**
 * StrategoController is the default implementation for the StrategoWebController interface,
 * however, it should NOT be aware that it is used in the context of a webserver:
 *
 * This class and all other classes in the logic-package (or future sub-packages)
 * should use 100% plain old Java Objects (POJOs). The use of Json, JsonObject or
 * Strings that contain encoded/json data should be avoided here.
 * Do not be afraid to create your own Java classes if needed.
 *
 * Note: Json and JsonObject can (and should) be used in the web-package however.
 *
 * (please update these comments in the final version)
 */
public class StrategoController implements StrategoWebController {

    public static PlayGame gameController = new PlayGame();

    private boolean firstSwitch=false;
    private String lastSwitch="";

    @Override
    public List<String> getStrategoVersions() {
        List<String> versions = new ArrayList<>();
        versions.add("original");
        versions.add("infiltrator");
        versions.add("duel");

        return versions;
    }

    @Override
    public int[] getStrategoVersion(String filter) {
        PionnenPerVersion pionnen=new PionnenPerVersion();
        List<String> allVersions=getStrategoVersions();
        for (String version : allVersions){
            if(version.equals(filter)){
                HashMap<String, int[]> arrayHashmap=pionnen.getAllArrayVersions();
                return arrayHashmap.get(filter);

            }
        }
        return null;
    }

    @Override
    public Map<String, String> joinGame(Map<String, String> gameInfo) {
        String version = gameInfo.get("version");
        String roomId = gameInfo.get("roomId");
        JsonArray board = new JsonArray(gameInfo.get("board"));

        return gameController.startGame(board, version, roomId);



    }

    @Override
    public Moves makeMove(Moves newMove, String parametersGameId, String gameId, String player) {
        Game game = gameController.getGameInProgress(gameId);
        Location attackerPawnLoc, defenderPawnLoc;

        if(player.equalsIgnoreCase("red")) {
            attackerPawnLoc = game.getLocation(newMove.getSrcX(), newMove.getSrcY());
            defenderPawnLoc = game.getLocation(newMove.getTarX(), newMove.getTarY());
        } else{
            attackerPawnLoc = game.getLocation(newMove.getSrcXReverse(), newMove.getSrcYReverse());
            defenderPawnLoc = game.getLocation(newMove.getTarXReverse(), newMove.getTarYReverse());
        }

        String playerToken = gameId+"-"+player;
        Boolean correctRedToken = player.equals("RED") && playerToken.equals(game.getRedPlayertoken());
        Boolean correctBlueToken = player.equals("BLUE") && playerToken.equals(game.getBluePlayertoken());
        Boolean attackIsMyPawn = false;
        Boolean defendIsTheirPawn = false;
        if(attackerPawnLoc != null) {
            attackIsMyPawn = attackerPawnLoc.getPlayer().equalsIgnoreCase(player);
        }
        if(defenderPawnLoc != null) {
            defendIsTheirPawn = defenderPawnLoc.getPlayer().equalsIgnoreCase(PlayGame.getInversePlayerColor(player));
        }


        if (game.activePlayer(player)){
            if(gameId.equals(parametersGameId)) {
                if (correctRedToken || correctBlueToken) {
                    //check if there is an attacker en defender -> attack
                    if(attackerPawnLoc != null && defenderPawnLoc != null && attackIsMyPawn && defendIsTheirPawn && newMove.isMovePossible(attackerPawnLoc)){
                        newMove = game.doAttack(newMove, attackerPawnLoc, defenderPawnLoc);
                        game.movelist.add(newMove);
                        game.switchPlayer();
                        return newMove;

                        //check if there is only an attacker -> move
                    } else if(attackerPawnLoc != null && attackIsMyPawn && newMove.isMovePossible(attackerPawnLoc)){
                        if(player.equalsIgnoreCase("red")) {
                            game.movePawn(attackerPawnLoc.getX(), attackerPawnLoc.getY(), newMove.getTarX(), newMove.getTarY());
                        }else{
                            game.movePawn(attackerPawnLoc.getX(), attackerPawnLoc.getY(), newMove.getTarXReverse(), newMove.getTarYReverse());
                        }
                        game.movelist.add(newMove);
                        game.switchPlayer();
                        return newMove;
                    }

                }
            }
        }
        return null;
    }


    @Override
    public List<Moves> getMoves(String gameId, String player, String parametersGameId) {
        Game game = gameController.getGameInProgress(gameId);
        String playerToken = gameId+"-"+player;
        if(game != null && gameId.equals(parametersGameId)){
            if(player.equals("RED") && playerToken.equals(game.getRedPlayertoken())){
                for (int i=2; i<game.movelist.size();i++){
                    Moves e =game.movelist.get(i);
                    if (i%2!=0 && !firstSwitch) {
                        e.getReverse();
                    }
                    if (firstSwitch){
                        if (!lastSwitch.equals("red")){
                            e.getReverse();
                        }
                    }
                }
                lastSwitch="red";
                firstSwitch=true;
                return game.movelist;
            } else if(player.equals("BLUE") && playerToken.equals(game.getBluePlayertoken())){
                for (int i=2; i<game.movelist.size();i++){
                    Moves e =game.movelist.get(i);
                    if (i%2==0 && !firstSwitch) {
                        e.getReverse();
                    }
                    if (firstSwitch){
                        if (!lastSwitch.equals("blue")){
                            e.getReverse();
                        }
                    }
                }
                lastSwitch="blue";
                firstSwitch=true;
                return game.movelist;
            }
        }
        return null;
    }

    @Override
    public Location[][] getGameState(String side, String gameId) {
        Game game = gameController.getGameInProgress(gameId);
        return game.showBoard(side);
    }
}
