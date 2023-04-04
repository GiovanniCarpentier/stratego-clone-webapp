package be.howest.ti.stratego2021.logic;

import io.vertx.core.json.JsonArray;

import java.util.Locale;

public class Board {

    private final int height = 10;
    private final int width = 10;
    private final int placeArea = 4;

    //rood onderaan & blauw bovenaan
    private final Location[][] boardLayout = new Location[10][10];

    public void addSide(JsonArray board, String side){
        String[][] convertedBoard = jsonToArray(board);
        int positionToPlaceX;
        int positionToPlaceY;
        for(int y = height-placeArea; y < height ; y++){

            if(side.equalsIgnoreCase("red")){
                //normale Y locatie
                positionToPlaceY = y;
            } else{
                //omgekeerde Y locatie
                positionToPlaceY = height - y - 1;
            }

            for(int x = 0; x < width; x++) {

                if(side.equalsIgnoreCase("red")){
                    //normale X locatie
                    positionToPlaceX = x;
                }else{
                    //omgekeerde X locatie
                    positionToPlaceX = width - x - 1;
                }
                if (convertedBoard[y][x] != null) {
                    boardLayout[positionToPlaceY][positionToPlaceX] = new Location(positionToPlaceX,positionToPlaceY,Pionnen.valueOf(convertedBoard[y][x].toUpperCase(Locale.ROOT)), side.toLowerCase(Locale.ROOT));
                } else {
                    boardLayout[positionToPlaceY][positionToPlaceX] = null;
                }
            }
        }


    }

    public void movePawn(int srcX, int srcY, int tarX, int tarY){
        Location loc = boardLayout[srcY][srcX];
        loc.setX(tarX);
        loc.setY(tarY);
        boardLayout[tarY][tarX] = loc;
        clearLocation(srcX, srcY);
    }

    public void clearLocation(int srcX, int srcY){
        boardLayout[srcY][srcX] = null;
    }

    public Pionnen getPawn(int x, int y){
        Pionnen pion = null;
        Location loc = boardLayout[y][x];
        if(loc != null){
            pion = loc.getPawn();
        }
        return pion;
    }

    public Location getLocation(int x, int y) {
        return boardLayout[y][x];
    }

    public Location[][] getBoard(String side){
        Location[][] boardToSend = new Location[10][10];
        int positionToPlaceX;
        int positionToPlaceY;
        for(int y = 0; y < height; y++){
            if(side.equalsIgnoreCase("red")){
                //normale Y locatie
                positionToPlaceY = y;
            } else{
                //omgekeerde Y locatie
                positionToPlaceY = height - y - 1;
            }
            for(int x = 0; x < width; x++){
                if(side.equalsIgnoreCase("red")){
                    //normale X locatie
                    positionToPlaceX = x;
                }else{
                    //omgekeerde X locatie
                    positionToPlaceX = width - x - 1;
                }

                Location originalLocation = boardLayout[y][x];
                if(originalLocation != null){

                    //hide andere kleur als het moet
                    if(originalLocation.getPlayer().equals(side.toLowerCase(Locale.ROOT))){
                        boardToSend[positionToPlaceY][positionToPlaceX] = originalLocation;
                    } else{
                        String otherColor = PlayGame.getInversePlayerColor(side);
                        Location onlyPlayerColor = new Location(positionToPlaceX,positionToPlaceY,null, otherColor);
                        boardToSend[positionToPlaceY][positionToPlaceX] = onlyPlayerColor;
                    }
                }else{
                    boardToSend[positionToPlaceY][positionToPlaceX] = null;
                }
            }
        }
        return boardToSend;
    }

    private String[][] jsonToArray(JsonArray board){
        //fixed size
        String[][] convertedBoard = new String[10][10];
        for(int y = 0; y < 10; y++){
            JsonArray currentRow = board.getJsonArray(y);
            for(int x = 0; x < 10; x++){
                convertedBoard[y][x] = currentRow.getString(x);
            }
        }
        return convertedBoard;
    }
}
