package org.battle;

import java.util.Scanner;

public class Board {
    private final String[][] board = new String[11][11];
    private int[] userCoordinates = new int[4];
    private boolean gameIsRunning = true;
    BoardServices bs = new BoardServices();


    public Board() {
        bs.setInitialGameBoard(this.board);
    }


    public void printFullBoard() {
        bs.setBoard(this.board);
    }
    public void printFogBoard() {
        bs.setFogBoard(this.board);
    }

    public void placeShip(Scanner scanner) {
        BattleShip[] ships = BattleShip.values();
        for (BattleShip ship : ships) {
            while (true) {
                System.out.println("\nEnter the coordinates of the " + ship.getName() + " (" + ship.getLength() + " cells):\n");
                try {
                    bs.readCoordinates(scanner, userCoordinates);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                bs.translateCoordinates(userCoordinates);
                try {
                    bs.validateMove(userCoordinates, this.board);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                int shipLength = bs.calculateShipLength(userCoordinates);
                if (shipLength != ship.getLength()) {
                    System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");
                    continue;
                }
                Ship shipInstance = new Ship(shipLength, userCoordinates);
                String[] shipSections = shipInstance.getShipSections();
                shipInstance.placeShipOnBoard(shipSections, this.board);

                printFullBoard();
                break;
            }
        }
    }


    public void shoot(int[] userCoordinates) {
        int x = userCoordinates[0];
        int y = userCoordinates[1];

        if (this.board[x][y].equals("O") || this.board[x][y].equals("X")) {
            this.board[x][y] = "X";
            if (bs.isShipSunk(userCoordinates, this.board)) {
                if (bs.isGameOver(this.board)) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    this.gameIsRunning = false;
                } else {
                    System.out.println("You sank a ship! Specify a new target:");
                }
            } else {
                System.out.println("You hit a ship!");
            }
        } else {
            this.board[x][y] = "M";
            System.out.println("You missed!");
        }
    }




    public boolean getGameIsRunning() {
        return this.gameIsRunning;
    }

    public int[] getUserCoordinates() {
        return userCoordinates;
    }

    public void setUserCoordinates(int[] userCoordinates) {
        this.userCoordinates = userCoordinates;
    }


}

