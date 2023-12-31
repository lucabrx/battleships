package org.battle;


public enum BattleShip {
    AIRCRAFT("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final int length;
    private final String name;

    BattleShip(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    public String getName() {
        return this.name;
    }
}

