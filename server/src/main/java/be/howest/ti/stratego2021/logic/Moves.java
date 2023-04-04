package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.core.json.JsonObject;

import java.util.Locale;

public class Moves {
    private final String player;
    private String attacker;
    private String defender;
    private String winner;
    private final JsonObject src;
    private final JsonObject tar;
    private boolean firstReverse=false;

    public Moves(String player, String attacker, String defender, String winner, JsonObject src, JsonObject tar) {
        this.player=player;
        this.attacker=attacker;
        this.defender=defender;
        this.winner=winner;
        this.src = src;
        this.tar = tar;
    }

    public String getPlayer() {
        return player.toLowerCase(Locale.ROOT);
    }

    @JsonIgnore
    public Boolean isMovePossible(Location attackerPawnLoc){
        if(attackerPawnLoc != null) {
            Pionnen pion = attackerPawnLoc.getPawn();
            int pionNumber = pion.getRank();
            if (!isWater()) {
                //bomb & vlag
                if(pionNumber == 0 || pionNumber == 1){
                    return false;

                //alle bewegende pionnen behalve scout
                }else if(pionNumber != 4 && isAroundPawn()){
                    return true;
                //scout
                }else if(pionNumber == 4){
                    Boolean isInSameLine = (getSrcX() == getTarX()) || (getSrcY() == getSrcY());
                    return isInSameLine;
                }
            }
        }
        return false;
    }

    private Boolean isAroundPawn(){
        Boolean isTarAbove = getSrcX() == getTarX() && (getSrcY() + 1 == getTarY());
        Boolean isTarBelow = getSrcX() == getTarX() && (getSrcY() - 1 == getTarY());
        Boolean isTarLeft = getSrcY() == getTarY() && (getSrcX() + 1 == getTarX());
        Boolean isTarRight = getSrcY() == getTarY() && (getSrcX() - 1 == getTarX());

        return isTarAbove || isTarBelow || isTarLeft || isTarRight;
    }

    @JsonIgnore
    private Boolean isWater(){
        for (waterLocation loc : waterLocation.values()) {
            if(getTarX() == loc.getX() && getTarY() == loc.getY()){
                return true;
            }
        }
        return false;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAttacker() {
        return attacker;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDefender() {
        return defender;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getWinner(){return winner;}


    public JsonObject getSrc() {
        return src;
    }

    public JsonObject getTar() {
        return tar;
    }

    @JsonIgnore
    public int getSrcX(){ return src.getInteger("col");}

    @JsonIgnore
    public int getSrcY(){ return src.getInteger("row");}

    @JsonIgnore
    public int getSrcXReverse(){
        int x = src.getInteger("col");
        return Math.abs(x-9);
    }

    @JsonIgnore
    public int getSrcYReverse(){
        int y = src.getInteger("row");
        return Math.abs(y-9);
    }

    @JsonIgnore
    public int getTarX(){ return tar.getInteger("col");}

    @JsonIgnore
    public int getTarY(){ return tar.getInteger("row");}

    @JsonIgnore
    public int getTarXReverse(){
        int x = tar.getInteger("col");
        return Math.abs(x-9);
    }

    @JsonIgnore
    public int getTarYReverse(){
        int y = tar.getInteger("row");
        return Math.abs(y-9);
    }

    public void setAttacker(String attacker) {
        this.attacker = attacker;
    }

    public void setDefender(String defender) {
        this.defender = defender;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void getReverse(){
        if (!firstReverse){
            swicthSrcTar();
            firstReverse=true;
        }else{
            swicthSrcTar();
                }
    }


    private void swicthSrcTar(){
        this.src.put("row", getSrcYReverse());
        this.src.put("col", getSrcXReverse());
        this.tar.put("row", getTarYReverse());
        this.tar.put("col", getTarXReverse());
    }
}
