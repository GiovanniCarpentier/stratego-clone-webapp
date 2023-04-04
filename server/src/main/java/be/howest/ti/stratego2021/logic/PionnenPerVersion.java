package be.howest.ti.stratego2021.logic;

import java.util.HashMap;

public class PionnenPerVersion {
    int[] originalArray= {1,6,1,0,8,5,4,4,4,3,2,1,1};
    int[] infiltratorArray= {1,6,1,1,7,5,4,4,4,3,2,1,1};
    int[] duelArray= {1,2,1,0,2,2,0,0,0,0,0,1,1};
    int[] miniArray= {1,0,0,1,0,0,0,0,0,0,0,0,1};
    int[] tinyArray= {1,0,0,0,0,0,0,0,0,0,0,0,1};
    private final String[] pionnen= {"flag","bomb","spy","infiltrator", "scout", "miner", "sergeant", "lieutenant", "captain", "major", "colonel", "general", "marshal" };
    private final HashMap<String, int[]> allArrayVersions=new HashMap<>();
    private void createHashMap(){
        allArrayVersions.put("original",originalArray);
        allArrayVersions.put("infiltrator",infiltratorArray);
        allArrayVersions.put("duel",duelArray);
        allArrayVersions.put("mini",miniArray);
        allArrayVersions.put("tiny",tinyArray);
    }

    public HashMap<String, int[]> getAllArrayVersions(){
        if (allArrayVersions.isEmpty()) {
            createHashMap();
        }
        return allArrayVersions;
    }

    public String[] getPionnen(){
        return pionnen;
    }



}
