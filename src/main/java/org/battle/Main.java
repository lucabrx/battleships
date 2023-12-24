package org.battle;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board p1 = new Board();
        Board p2 = new Board();
        Board[] players = {p1, p2};
        int player = 0;

        System.out.println("Player " + (player + 1) + ", place your ships on the game field \n");
        p1.printFullBoard();
        p1.placeShip(scanner);
        System.out.println("\nPress Enter and pass the move to another player");
        scanner.nextLine();
        System.out.print("...");
        scanner.nextLine();


        player = 1;
        System.out.println("Player " + (player + 1) + ", place your ships on the game field \n");
        p2.printFullBoard();
        p2.placeShip(scanner);
        System.out.println("\nPress Enter and pass the move to another player");
        scanner.nextLine();
        System.out.print("...");
        scanner.nextLine();



        while (true) {

            player = player == 0 ? 1 : 0;
            int enemyBoardIdx = player == 0 ? 1 : 0;
            players[enemyBoardIdx].printFogBoard();
            System.out.println("---------------------");
            players[player].printFullBoard();


            System.out.println("\nPlayer " + (player + 1) + " it's your turn:\n");

            int x, y;
            try {
                StringBuilder cord = new StringBuilder(scanner.next().trim().toUpperCase());
                y = cord.charAt(0);
                cord.deleteCharAt(0);
                x = Integer.parseInt(cord.toString());
            } catch (Exception e) {
                System.out.println("Error! Invalid input. Please enter the coordinates again.");
                continue;
            }
            int[] cords = players[enemyBoardIdx].getUserCoordinates();
            cords[0] = y;
            cords[1] = x;
            players[enemyBoardIdx].setUserCoordinates(cords);
            players[enemyBoardIdx].bs.translateCoordinates(cords);
            if (!players[enemyBoardIdx].bs.validateOutboundMove(cords)) {
                continue;
            }
            System.out.println();
            players[enemyBoardIdx].shoot(cords);
            if(!players[enemyBoardIdx].getGameIsRunning()) {
                break;
            }
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
            System.out.print("...");
            scanner.nextLine();





        }

    }

}
