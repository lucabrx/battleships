package org.battle;

public class Ship {
    private final int shipLength;
    private final String[] shipSections;
    private final int[] userCoordinates;

    public Ship(int shipLength, int[] userCoordinates) {
        this.shipLength = shipLength;
        this.shipSections = new String[shipLength];
        this.userCoordinates = userCoordinates;
    }


    public String[] getShipSections() {
        calculateSections();
        return shipSections;
    }

    public void calculateSections() {
        String x, y;
        int subtractionModifier;

        if (userCoordinates[0] == userCoordinates[2]) {
            subtractionModifier = userCoordinates[1] > userCoordinates[3] ? -1 : 1;

            for (int i = 0; i < shipLength; i++) {
                y = String.valueOf((char) (userCoordinates[0] + 64));
                x = Integer.toString(userCoordinates[1] + (i * subtractionModifier));
                shipSections[i] = y + x;
            }
        } else {
            subtractionModifier = userCoordinates[0] > userCoordinates[2] ? -1 : 1;
            for (int i = 0; i < shipLength; i++) {
                y = String.valueOf((char) (userCoordinates[0] + (i * subtractionModifier) + 64));
                x = Integer.toString(userCoordinates[1]);
                shipSections[i] = y + x;
            }
        }
    }

    public void placeShipOnBoard(String[] shipSections, String[][] board) {
        for (String section : shipSections) {

            int y = section.charAt(0) - 64;
            int x = Integer.parseInt(section.substring(1));
            board[y][x] = "O";
        }
    }

}