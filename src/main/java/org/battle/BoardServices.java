package org.battle;

import java.util.Scanner;

public class BoardServices {


    public void setInitialGameBoard(String[][] board) {
        char tempChar;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i == 0) {
                    if (j == 0) {  // [0][0] should be empty
                        board[i][j] = " ";
                    } else { // numbers from 1 to 10 but
                        board[i][j] = String.valueOf(j);
                    }
                }  else if (j == 0) { // letters from a-j
                    tempChar = (char) (i + 64);
                    board[i][j] = String.valueOf(tempChar);
                } else { // rest of the field should be ~
                    board[i][j] = "~";
                }
            }
        }
    }

    public void setFogBoard(String[][] board) {
        char tempChar;
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 1; i < 11; i++) {
            tempChar = (char) (i + 64);
            System.out.print(tempChar + " ");
            for (int j = 1; j < 11; j++) {
                if (board[i][j].equals("O")) {
                    System.out.print("~ ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
                if (j == 10) {
                    System.out.println();
                }
            }
        }
    }

    public void setBoard(String[][] board) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("  ");
                } else if (i == 0) {
                    System.out.print(board[i][j] + " ");
                } else if (j == 0) {
                    System.out.print(board[i][j] + " ");
                } else {
                    switch (board[i][j]) {
                        case "O" -> System.out.print("O ");
                        case "X" -> System.out.print("X ");
                        case "M" -> System.out.print("M ");
                        default -> System.out.print("~ ");
                    }
                }
                if (j == 10) {
                    System.out.println();
                }
            }
        }
    }



    // subtract 64 from the char value to get the correct int value
    public void translateCoordinates(int[] userCoordinates) {
        int charToIntValue = 64;
        userCoordinates[0] = userCoordinates[0] - charToIntValue;
        userCoordinates[2] = userCoordinates[2] - charToIntValue;
    }

    public boolean validateOutboundMove(int[] userCoordinates) {
        int x = userCoordinates[0];
        int y = userCoordinates[1];
        if (x < 1 || x > 10 || y < 1 || y > 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
        return true;
    }

    public void validateMove(int[] userCoordinates, String[][] board) throws Exception {
        int x1 = userCoordinates[0];
        int x2 = userCoordinates[2];
        int y1 = userCoordinates[1];
        int y2 = userCoordinates[3];
        if (x1 < 1 || x1 > 10 || x2 < 1 || x2 > 10 || y1 < 1 || y1 > 10 || y2 < 1 || y2 > 10) {
            throw new Exception("Error! You entered the wrong coordinates! Try again:");
        }
        if (x1 == x2) {
            if (y1 > y2) {
                for (int i = y2; i <= y1; i++) {
                    if (isCellValid(x1, i, board)) {
                        throw new Exception("Error! You placed it too close to another one. Try again:");
                    }
                }
            } else {
                for (int i = y1; i <= y2; i++) {
                    if (isCellValid(x1, i, board)) {
                        throw new Exception("Error! You placed it too close to another one. Try again:");
                    }
                }
            }
        } else if (y1 == y2) {
            if (x1 > x2) {
                for (int i = x2; i <= x1; i++) {
                    if (isCellValid(i, y1, board)) {
                        throw new Exception("Error! You placed it too close to another one. Try again:");
                    }
                }
            } else {
                for (int i = x1; i <= x2; i++) {
                    if (isCellValid(i, y1, board)) {
                        throw new Exception("Error! You placed it too close to another one. Try again:");
                    }
                }
            }
        } else {
            throw new Exception("Error! You placed it too close to another one. Try again:");
        }
    }

    private boolean isCellValid(int x, int y, String[][] board) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 1 && i <= 10 && j >= 1 && j <= 10) {
                    if (board[i][j].equals("O")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int calculateShipLength(int[] userCoordinates) {
        int length, x, y;
        x = userCoordinates[0] - userCoordinates[2];
        y = userCoordinates[1] - userCoordinates[3];
        length = Math.abs(x) + Math.abs(y) + 1;
        return length;
    }

    public void readCoordinates(Scanner scanner, int[] userCoordinates) throws Exception {
        int x1, x2, y1, y2;
        try {
            StringBuilder cord1 = new StringBuilder(scanner.next().trim().toUpperCase());
            StringBuilder cord2 = new StringBuilder(scanner.next().trim().toUpperCase());

            y1 = cord1.charAt(0);
            cord1.deleteCharAt(0);
            y2 = cord2.charAt(0);
            cord2.deleteCharAt(0);
            x1 = Integer.parseInt(cord1.toString());
            x2 = Integer.parseInt(cord2.toString());
        } catch (Exception e) {
            throw new Exception("Error! Invalid input. Please enter the coordinates again.");
        }

        userCoordinates[0] = y1; // example: A
        userCoordinates[1] = x1; // example: 1 y1 + x1 = A1
        userCoordinates[2] = y2; // example: A
        userCoordinates[3] = x2; // example: 5  y2 + x2 = A5

    }

    public boolean isGameOver(String[][] board) {
        for (String[] strings : board) {
            for (String string : strings) {
                if (string.equals("O")) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isShipSunk(int[] userCoordinates, String[][] board) {
        int x = userCoordinates[0];
        int y = userCoordinates[1];

        // Check cells in the up direction
        for (int i = x - 1; i >= 1; i--) {
            if (board[i][y].equals("O")) {
                return false;
            }
            if (board[i][y].equals("X")) {
                continue;
            }
            break;
        }

        // Check cells in the down direction
        for (int i = x + 1; i <= 10; i++) {
            if (board[i][y].equals("O")) {
                return false;
            }
            if (board[i][y].equals("X")) {
                continue;
            }
            break;
        }

        // Check cells in the left direction
        for (int i = y - 1; i >= 1; i--) {
            if (board[x][i].equals("O")) {
                return false;
            }
            if (board[x][i].equals("X")) {
                continue;
            }
            break;
        }

        // Check cells in the right direction
        for (int i = y + 1; i <= 10; i++) {
            if (board[x][i].equals("O")) {
                return false;
            }
            if (board[x][i].equals("X")) {
                continue;
            }
            break;
        }

        return true;
    }



}
