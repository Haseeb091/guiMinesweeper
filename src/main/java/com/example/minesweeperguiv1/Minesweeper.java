package com.example.minesweeperguiv1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Minesweeper extends Application {
    private boolean isFirstClick=true;
    private Stage stage;
    private boolean clickable=true;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage=stage;
        rowColInputPage();


    }
// get the 2d array from grid and sets event listener and displays gui grid
    public void mainGame(int row,int col){

        clickable=true;
        isFirstClick=true;
        stage.setTitle("Minesweeper");
        GridPane gridPane = new GridPane();

        Grid g=new Grid(row,col);

        for (int rowI = 0; rowI < row; rowI++) {

            for (int colI = 0; colI < col; colI++) {
                    g.getTile(rowI,colI).setOnMouseClicked(event -> {
                    // if game won or lost then you cant click
                    if(clickable) {
                        MouseButton button = event.getButton();

                        if (button == MouseButton.PRIMARY) {

                            int rowIndex = GridPane.getRowIndex((Node) event.getSource());
                            int colIndex = GridPane.getColumnIndex((Node) event.getSource());
                            if (isFirstClick) {
                                // if its first click only run if its not flagged
                                if(!g.getIsFlagged(rowIndex, colIndex)){
                                    g.firstMoveSetup(rowIndex, colIndex);
                                    isFirstClick = false;
                                }

                            } else {
                                g.setTilesVisible(rowIndex, colIndex);
                                if (g.gameWon()) {
                                    clickable=false;
                                    //after 10 seconds you can see the win page
                                    Timeline winTimeline = new Timeline(new KeyFrame(Duration.seconds(10), ev -> winPage()));
                                    winTimeline.setCycleCount(1);// keep to 1 otherwise it will repeat
                                    winTimeline.play();

                                } else if (g.isMine(rowIndex, colIndex)&&!g.getIsFlagged(rowIndex,colIndex)) {
                                    clickable=false;
                                    g.showAllMines();

                                    Timeline looseTimeline = new Timeline(new KeyFrame(Duration.seconds(10), ev -> losePage()));
                                    looseTimeline.setCycleCount(1);
                                    looseTimeline.play();


                                }

                            }
                        } else if (button == MouseButton.SECONDARY) {
                            int rowIndex = GridPane.getRowIndex((Node) event.getSource());
                            int colIndex = GridPane.getColumnIndex((Node) event.getSource());

                            g.flag(rowIndex, colIndex);


                        }

                    }
                });
                gridPane.add(g.getTile(rowI,colI),colI,rowI,1,1);
            }


        }




        Scene scene = new Scene(gridPane, col*20, row*20);
        stage.setScene(scene);
        stage.show();
    }
    public void winPage(){
        Label winLabel=new Label("You won  ");
        Button restart=new Button("restart");
        restart.setOnMouseClicked(event -> {

            MouseButton button = event.getButton();

            if (button == MouseButton.PRIMARY) {

                rowColInputPage();

            }

        });
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(winLabel,1,1,1,1);
        gridPane.add(restart,2,1,1,1);
        Scene scene =new Scene(gridPane, 240, 100);

        stage.setScene(scene);
        stage.show();
    }
    public void losePage(){
        Label loseLabel=new Label("You lost  ");
        Button restart=new Button("restart");
        restart.setOnMouseClicked(event -> {

                MouseButton button = event.getButton();

                if (button == MouseButton.PRIMARY) {

                    rowColInputPage();

                }

        });
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(loseLabel,1,1,1,1);
        gridPane.add(restart,2,1,1,1);
        Scene scene =new Scene(gridPane, 240, 100);

        stage.setScene(scene);
        stage.show();
    }
    public void rowColInputPage(){
        TextField row=new TextField();
        TextField col=new TextField();
        Button play=new Button("play");
        play.setOnMouseClicked(event -> {
            MouseButton button = event.getButton();

            if(button==MouseButton.PRIMARY){// left click
                try {
                    int rowValue=Integer.parseInt(row.getText());
                    int colValue=Integer.parseInt(col.getText());
                    if((rowValue < 5 || rowValue > 30)){

                        throw new Exception("Not_in_range");
                    }
                    if((colValue < 5 || colValue > 30)){

                        throw new Exception("Not_in_range");
                    }
                    mainGame(rowValue,colValue);

                }catch (NumberFormatException e){
                    //gets the exception if its not an int
                    Alert notAnumb = new Alert(Alert.AlertType.WARNING);
                    notAnumb.setContentText("please enter valid integers");

                    notAnumb.show();


                }
                catch (Exception e){
                    // checks if its not in number range
                    if(e.toString().contains("Not_in_range")){

                        Alert notInRange = new Alert(Alert.AlertType.WARNING);
                        notInRange.setContentText("please enter valid integers between 5 -30");

                        notInRange.show();
                    }

                }


            }

        });

        GridPane gridPane = new GridPane();
        gridPane.add(new Label("row"),1,1,1,1);// first input is col,then row
        gridPane.add(row,2,1,1,1);

        gridPane.add(new Label("col"),1,2,1,1);
        gridPane.add(col,2,2,1,1);
        gridPane.add(play,3,3,1,1);
        VBox vBox=new VBox(new Label("please enter row and columns below between 5-30"),gridPane);
        vBox.setSpacing(2);

        Scene scene =new Scene(vBox, 300, 100);

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}