package com.example.minesweeperguiv1;

import java.util.Scanner;

public class Minesweeper {
    private Grid grid;

    private boolean gameWon;
    private Scanner scanner;
    private boolean gameLost;
    private boolean firstMove = true;
    private int rowMax = 0;
    private int colMax = 0;

    public Minesweeper() {
        gameLost = false;
        gameWon = false;
        scanner = new Scanner(System.in);
        startGame();

    }

    public void setupRowAndCols() {
        int row = 0;
        int col = 0;
        //insures numbers are within range
        while (row < 5 || row > 100) {
            System.out.println("please Enter Number of rowsbetween 5-100");
            //if there is no int then loop until they enter one
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Please enter a valid  Number of rows");
            }
            row = scanner.nextInt();

        }
        while (col < 5 || col > 100) {
            System.out.println("please Enter Number of columns between 5-100");
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Please enter a valid  Number of columns");
            }
            col = scanner.nextInt();

        }


        rowMax = row;
        colMax = col;
        grid = new Grid(row, col);
        grid.printAll();

    }

    private void getCordinates(char value) {
        int rowCordinate = -1;//-1 because it would never enter loop
        int colCordinate = -1;
        while (rowCordinate < 0 || rowCordinate > rowMax - 1) {
            System.out.println("Please enter a valid row for tile between 0-" + (rowMax - 1));
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Please enter a valid row for tile between 0-" + (rowMax - 1));
            }
            rowCordinate = scanner.nextInt();
        }

        while (colCordinate < 0 || colCordinate > colMax - 1) {
            System.out.println("Please enter a valid col for tile between 0-" + (colMax - 1));
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Please enter a valid col for tile between 0-" + (colMax - 1));
            }
            colCordinate = scanner.nextInt();
        }
        optionsSelector(rowCordinate, colCordinate, value);// depending on value a diffrent method is called to handle input

    }

    private void optionsSelector(int rowCordinate, int colCordinate, char value) {
        if (value == 'v') {
            if (!grid.getIsFlagged(rowCordinate, colCordinate)) {
                if (firstMove) {
                    grid.firstMoveSetup(rowCordinate, colCordinate);
                    firstMove = false;
                    grid.printAll();
                } else {
                    grid.setTilesVisible(rowCordinate, colCordinate);

                    if (grid.isMine(rowCordinate, colCordinate)) {

                        grid.showAllMines();

                        gameLost = true;
                    } else if (grid.gameWon()) {

                        gameWon = true;
                    }
                    grid.printAll();
                }

            } else {
                grid.printAll();
            }
        } else if (value == 'f') {
            grid.flag(rowCordinate, colCordinate);
            grid.printAll();
        } else if (value == 'u') {
            grid.unFlag(rowCordinate, colCordinate);
            grid.printAll();
        }

    }

    public void startGame() {
        setupRowAndCols();

        while (!gameWon && !gameLost) {
            String moveChoice = "";
            boolean validInput = false;
            while (!validInput) {
                System.out.println("v-Make tile visible, f-flag, u-unflag ");
                char c = scanner.next().charAt(0);
                if (c == 'v' || c == 'f' || c == 'u') {


                    getCordinates(c);
                    validInput = true;
                    // gameWon=true;
                }

            }


        }

        if (gameWon) {

            System.out.println("you won");
        } else if (gameLost) {

            System.out.println("you lost");
        }

    }
}
