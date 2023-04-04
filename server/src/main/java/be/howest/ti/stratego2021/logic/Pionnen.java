package be.howest.ti.stratego2021.logic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

public enum Pionnen {
    FLAG(0),
    BOMB(1),
    SPY(2),
    INFILTRATOR(3),
    SCOUT(4),
    MINER(5),
    SERGEANT(6),
    LIEUTENANT(7),
    CAPTAIN(8),
    MAJOR(9),
    COLONEL(10),
    GENERAL(11),
    MARSHAL(12);
    private final int rank;

    Pionnen(int rank) {
        this.rank=rank;
    }

    public int getRank(){
        return rank;
    }

    @Override @JsonProperty("name")
    public String toString(){return this.name().toLowerCase(Locale.ROOT);}
}
