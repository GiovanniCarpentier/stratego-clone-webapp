package be.howest.ti.stratego2021.logic;

public enum waterLocation {
    LOC1(2,4),
    LOC2(3,4),
    LOC3(2,5),
    LOC4(3,5),

    LOC5(6,4),
    LOC6(7,4),
    LOC7(6,5),
    LOC8(7,5);

    private final int x;
    private final int y;

    waterLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
