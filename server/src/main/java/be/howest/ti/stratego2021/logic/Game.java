package be.howest.ti.stratego2021.logic;

import io.vertx.core.json.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String version = null;
    private final int gameNumber;
    private String bluePlayertoken = null;
    private String redPlayertoken = null;
    private final Board currentBoard = new Board();
    public String activePlayer = "RED";
    List<Moves> movelist = new ArrayList<>();
    List<Moves> redmovelist = new ArrayList<>();
    List<Moves> bluemovelist = new ArrayList<>();
    private final Attack attackManager = new Attack();

    public Game(String version, int gameNumber){
        this.version = version;
        this.gameNumber = gameNumber;
    }

    public Location[][] showBoard(String side){
        return this.currentBoard.getBoard(side);
    }

    public String getBluePlayertoken() {
        return bluePlayertoken;
    }

    public String getRedPlayertoken() {
        return redPlayertoken;
    }

    public boolean activePlayer(String player) { return player.equals(activePlayer); }

    public int getGameNumber() {
        return gameNumber;
    }

    public void addPlayer(String token, JsonArray board){
        String side = this.activePlayer;
        if(side.equalsIgnoreCase("red")){
            this.redPlayertoken = token;
        }else{
            this.bluePlayertoken = token;
        }
        currentBoard.addSide(board, side);
        switchPlayer();
    }

    public void emptyMove(String player){
        movelist.add(new Moves(player, null, null, null, null, null));
        redmovelist.add(new Moves(player, null, null, null, null, null));
        bluemovelist.add(new Moves(player, null, null, null, null, null));
    }

    public Pionnen getPawn(int x, int y){
        return currentBoard.getPawn(x,y);
    }

    public Location getLocation(int x, int y){
        return currentBoard.getLocation(x,y);
    }

    public void switchPlayer(){
        if (activePlayer.equals("RED")){
            activePlayer = "BLUE";
        } else {
            activePlayer = "RED";
        }
    }





    public Moves doAttack(Moves newMove, Location attackerPawnLoc, Location defenderPawnLoc) {
        String attackName = attackerPawnLoc.getPawnName();
        String defendName = defenderPawnLoc.getPawnName();
        String winner = attackManager.getWinner(attackName, defendName);
        newMove.setAttacker(attackName);
        newMove.setDefender(defendName);
        newMove.setWinner(winner);
        if(winner.equalsIgnoreCase("attacker")){
            movePawn(attackerPawnLoc.getX(), attackerPawnLoc.getY(), defenderPawnLoc.getX(), defenderPawnLoc.getY());
        }else if(winner.equalsIgnoreCase("defender")){
            currentBoard.clearLocation(attackerPawnLoc.getX(), attackerPawnLoc.getY());
        } else if (winner.equalsIgnoreCase("draw")){
            currentBoard.clearLocation(attackerPawnLoc.getX(), attackerPawnLoc.getY());
            currentBoard.clearLocation(defenderPawnLoc.getX(), defenderPawnLoc.getY());
        }

        return newMove;
    }

    public void movePawn(int srcX, int srcY, int tarX, int tarY){
        currentBoard.movePawn(srcX, srcY, tarX, tarY);
    }


}
