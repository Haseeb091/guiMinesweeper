package com.example.minesweeperguiv1;



import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Grid {
    private Tile[][] grid;
    private int row;
    private int col;
    private Set<Point2D> emptyPos = new HashSet<Point2D>();// holds the empty position and when size is 0 game won

    public Grid(int row, int col) {
        this.row = row;
        this.col = col;
        grid = new Tile[row][col];
        initilize();
        // intitaze grid


    }
    public Tile[][] getGrid(){

        return grid;
    }

    //iff the tile is not visible then you can flag it
    public void flag(int rowTemp, int colTemp) {
        if (!grid[rowTemp][colTemp].getisVisible()) {
            if (!grid[rowTemp][colTemp].getisFlagged()) {
                grid[rowTemp][colTemp].setisFlagged(true);
            }else {
                grid[rowTemp][colTemp].setisFlagged(false);
            }
        }
    }

    public boolean getIsFlagged(int rowTemp, int colTemp) {

        return grid[rowTemp][colTemp].getisFlagged();
    }

    // removing flag - so you can make tile visible later
    private void unFlag(int rowTemp, int colTemp) {
        grid[rowTemp][colTemp].setisFlagged(false);
    }

    //initializes 2d array with tiles which have no mines
    private void initilize() {


        for (int rowI = 0; rowI < row; rowI++) {

            for (int colI = 0; colI < col; colI++) {

                grid[rowI][colI] = new Tile(0);



            }


        }

    }

    //once you loose then all mines are displayed
    public void showAllMines() {
        for (int rowI = 0; rowI < row; rowI++) {

            for (int colI = 0; colI < col; colI++) {
                if (grid[rowI][colI].getisMine()) {
                    unFlag(rowI,colI);
                    setTilesVisible(rowI, colI);

                }

            }


        }


    }

    //there are no mines when first move is made
    // then the mines are generated and numbers and the empty position set is added to
    public void firstMoveSetup(int rowC, int colC) {
        grid[rowC][colC].setisVisible(true);
        genMines();
        generateNumbers();
        populateEmpties();
        setTilesVisible(rowC, colC);// insures if first pos has no mines or numbers all adacent empty pos are revieled
    }

    // randomly generates mines with the chance of 20 percent
    private void genMines() {
        int minesCount=0;
        for (int rowI = 0; rowI < row; rowI++) {

            for (int colI = 0; colI < col; colI++) {
                if (!grid[rowI][colI].getisVisible()) {

                    if (Math.random() <= 0.05) {
                        minesCount++;
                        grid[rowI][colI].setisMine(true);


                    }
                }


            }


        }

        if (minesCount==0){
            genMines();
        }
    }

    // checks all positions to get surounding number of mines and sets the value in tile
    private void generateNumbers() {
        for (int rowI = 0; rowI < row; rowI++) {
            ArrayList<int[]> coordinateIncrementList = new ArrayList<int[]>();

            coordinateIncrementList.add(new int[]{-1,0});
            coordinateIncrementList.add(new int[]{1,0});
            coordinateIncrementList.add(new int[]{0,-1});
            coordinateIncrementList.add(new int[]{0,1});

            coordinateIncrementList.add(new int[]{1,1});
            coordinateIncrementList.add(new int[]{-1,-1});
            coordinateIncrementList.add(new int[]{1,-1});
            coordinateIncrementList.add(new int[]{-1,1});
            for (int colI = 0; colI < col; colI++) {
                if (!grid[rowI][colI].getisMine()) {
                    int minesCount = 0;
                    for (int[] cordinate:coordinateIncrementList){
                        int rowCordinate=cordinate[0]+rowI;
                        int colCordinate=cordinate[1]+colI;

                        if (checkIfInIndex(rowCordinate, row) && checkIfInIndex(colCordinate, col) && grid[rowCordinate][colCordinate].getisMine()) {

                            minesCount++;
                        }
                    }
//                    if (checkIfInIndex(rowI + 1, row) && checkIfInIndex(colI, col) && grid[rowI + 1][colI].getisMine()) {
//
//                        minesCount++;
//                    }
//                    if (checkIfInIndex(rowI - 1, row) && checkIfInIndex(colI, col) && grid[rowI - 1][colI].getisMine()) {
//
//                        minesCount++;
//                    }
//                    if (checkIfInIndex(rowI, row) && checkIfInIndex(colI - 1, col) && grid[rowI][colI - 1].getisMine()) {
//
//                        minesCount++;
//                    }
//                    if (checkIfInIndex(rowI, row) && checkIfInIndex(colI + 1, col) && grid[rowI][colI + 1].getisMine()) {
//
//                        minesCount++;
//                    }//here
//                    if (checkIfInIndex(rowI + 1, row) && checkIfInIndex(colI + 1, col) && grid[rowI + 1][colI + 1].getisMine()) {
//
//                        minesCount++;
//                    }
//                    if (checkIfInIndex(rowI - 1, row) && checkIfInIndex(colI - 1, col) && grid[rowI - 1][colI - 1].getisMine()) {
//
//                        minesCount++;
//                    }
//                    if (checkIfInIndex(rowI - 1, row) && checkIfInIndex(colI + 1, col) && grid[rowI - 1][colI + 1].getisMine()) {
//
//                        minesCount++;
//                    }
//                    if (checkIfInIndex(rowI + 1, row) && checkIfInIndex(colI - 1, col) && grid[rowI + 1][colI - 1].getisMine()) {
//
//                        minesCount++;
//                    }

                    if (minesCount > 0) {
                        grid[rowI][colI].setValue(minesCount);
                        // grid[rowI][colI].setisVisible(true);
                    }

                }

            }


        }

    }

    // populating set of empty position -ie not mines
    //used to see if game has been won
    private void populateEmpties() {

        for (int rowI = 0; rowI < row; rowI++) {

            for (int colI = 0; colI < col; colI++) {
                if (!grid[rowI][colI].getisMine() && !grid[rowI][colI].getisVisible()) {
                    emptyPos.add(new Point2D(rowI, colI));
                }

            }


        }
    }

    // set method to make tiles visible treated diffrently depending on if there is a mine, number>0, 0
    public void setTilesVisible(int tempRow, int tempCol) {

        if(!grid[tempRow][tempCol].getisFlagged()) {

            if (grid[tempRow][tempCol].getisMine()) {
                grid[tempRow][tempCol].setisVisible(true);

            } else {
                if (grid[tempRow][tempCol].getValue() == 0) {
                    displayEmpties(tempRow, tempCol);// will set this pos to visible aswell
                } else {
                    setNonMineTileVisible(tempRow, tempCol);
                }
            }
        }
    }

    public boolean isMine(int temprow, int tempCol) {
        return grid[temprow][tempCol].getisMine();


    }

    public boolean gameWon() {

        return emptyPos.size() == 0;
    }

    //sets to visible and removes from set
    private void setNonMineTileVisible(int temprow, int tempCol) {
        if(!isMine(temprow,tempCol)){

            grid[temprow][tempCol].setisVisible(true);

            emptyPos.remove(new Point2D(temprow, tempCol));

        }



    }

    private void displayEmpties(int tempRow, int tempCol) {

        ArrayList<int[]> emptyToCheck = new ArrayList<int[]>();

        emptyToCheck.add(new int[]{tempRow, tempCol});
        setNonMineTileVisible(tempRow, tempCol);
        //goes through all adacent tiles and addes them on to list if there not visible and dont have mine or number >0
        // used to display all empty tiles
        ArrayList<int[]> coordinateIncrementList = new ArrayList<int[]>();

        coordinateIncrementList.add(new int[]{-1,0});
        coordinateIncrementList.add(new int[]{1,0});
        coordinateIncrementList.add(new int[]{0,-1});
        coordinateIncrementList.add(new int[]{0,1});
        while (!emptyToCheck.isEmpty()) {
            int[] cordinates = emptyToCheck.get(0);

            for (int[] coordinateIncrement:coordinateIncrementList){

                int newCoordinateRow=cordinates[0] +coordinateIncrement[0];
                int newCoordinateCol=cordinates[1] +coordinateIncrement[1];

                if (checkIfNotMineAndNotVisibleAndNotNumbers(newCoordinateRow, newCoordinateCol)) {

                    unFlag(newCoordinateRow, newCoordinateCol);
                    setNonMineTileVisible(newCoordinateRow, newCoordinateCol);
                    emptyToCheck.add(new int[]{newCoordinateRow, newCoordinateCol});

                }else if (checkIfNotMineAndNotVisible(newCoordinateRow, newCoordinateCol)){
                    unFlag(newCoordinateRow, newCoordinateCol);
                    setNonMineTileVisible(newCoordinateRow, newCoordinateCol);

                }

            }



            emptyToCheck.remove(0);

        }


    }

//    private void recursiveDisplayEmpties(int tempRow, int tempCol) {
//
//
//
//        setNonMineTileVisible(tempRow, tempCol);
//       // has list of all cordinates so no need for multiple if statments
//        ArrayList<int[]> coordinateIncrementList = new ArrayList<int[]>();
//
//        coordinateIncrementList.add(new int[]{-1,0});
//        coordinateIncrementList.add(new int[]{1,0});
//        coordinateIncrementList.add(new int[]{0,-1});
//        coordinateIncrementList.add(new int[]{0,1});
//
//        coordinateIncrementList.add(new int[]{1,1});
//        coordinateIncrementList.add(new int[]{-1,-1});
//        coordinateIncrementList.add(new int[]{1,-1});
//        coordinateIncrementList.add(new int[]{-1,1});
//
//
//            int[] cordinates = new int[]{tempRow,tempCol};
//
//            for (int[] coordinateIncrement:coordinateIncrementList){
//
//                int newCoordinateRow=cordinates[0] +coordinateIncrement[0];
//                int newCoordinateCol=cordinates[1] +coordinateIncrement[1];
//
//                if (checkIfNotMineAndNotVisibleAndNotNumbers(newCoordinateRow, newCoordinateCol)) {
//
//                    unFlag(newCoordinateRow, newCoordinateCol);
//                    setNonMineTileVisible(newCoordinateRow, newCoordinateCol);
//                    recursiveDisplayEmpties(newCoordinateRow,newCoordinateCol);
//
//                }else if (checkIfNotMineAndNotVisible(newCoordinateRow, newCoordinateCol)){
//                    unFlag(newCoordinateRow, newCoordinateCol);
//                    setNonMineTileVisible(newCoordinateRow, newCoordinateCol);
//
//                }
//
//            }
//
//
//
//
//
//
//    }


    private boolean checkIfNotMineAndNotVisibleAndNotNumbers(int tempRow, int tempCol) {

      return checkIfNotMineAndNotVisible(tempRow,tempCol)&& grid[tempRow][tempCol].getValue() == 0;
    }
    private boolean checkIfNotMineAndNotVisible(int tempRow, int tempCol) {

        if (checkIfInIndex(tempRow, row) && checkIfInIndex(tempCol, col) && !grid[tempRow][tempCol].getisMine()
                && !grid[tempRow][tempCol].getisVisible() ) {
            return true;


        }
        return false;
    }
    private boolean checkIfInIndex(int val, int indexMax) {
        //avoiding index out of bounds

        if (val >= 0 && val < indexMax) {
            return true;
        } else {
            return false;
        }
    }

    public Tile getTile(int tempRow,int tempCol){
        if(checkIfInIndex(tempRow,this.row)&&checkIfInIndex(tempCol,this.col)){
            return grid[tempRow][tempCol];
        }
        return null;

    }


}
