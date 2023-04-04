package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

public class Location {
    private final Pionnen pawn;
    private final String pawnName;
    private final String player;
    private int x;
    private int y;

    public Location(int x,int y,Pionnen pawn, String player) {
        this.x = x;
        this.y = y;

        this.pawn = pawn;
        if(this.pawn == null){
            this.pawnName = null;
        }else {
            this.pawnName = this.pawn.name().toLowerCase(Locale.ROOT);
        }
        this.player = player;
    }



    @JsonIgnore
    public Pionnen getPawn() {
        return pawn;
    }

    @JsonProperty("rank")
    public String getPawnName() {
        return pawnName;
    }

    public String getPlayer() {
        return player;
    }

    @JsonIgnore
    public int getX() {
        return x;
    }

    @JsonIgnore
    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
}
