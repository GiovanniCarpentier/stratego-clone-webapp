package be.howest.ti.stratego2021.logic;

import java.util.Locale;

public class  Attack {
    public String getWinner(String attacker, String defender) {
        int attackerRank=Pionnen.valueOf(attacker.toUpperCase(Locale.ROOT)).getRank();
        int defenderRank=Pionnen.valueOf(defender.toUpperCase(Locale.ROOT)).getRank();
        return checkForExeptions(attackerRank, defenderRank);
    }

    public String checkForExeptions(int attackerRank, int defenderRank) {
        if (defenderRank==1 && attackerRank!=5){
            return "defender";
        }else if(attackerRank==2 && defenderRank==12) {
            return "attacker";
        }else if (attackerRank==defenderRank){
            return "draw";
        }else if (attackerRank>defenderRank){
            return "attacker";
        }else{
            return "defender";
        }
    }

}
