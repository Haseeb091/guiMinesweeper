package com.example.minesweeperguiv1;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private boolean isFirstClick=true;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public void mainGame(Stage stage,int row,int col){

        stage.setTitle("GridPane Experiment");
        GridPane gridPane = new GridPane();
        Grid g=new Grid(row,col);
        Tile[][] tempGrid=g.getGrid();
        for (int rowI = 0; rowI < row; rowI++) {

            for (int colI = 0; colI < col; colI++) {

                tempGrid[rowI][colI] = new Tile(0);
                tempGrid[rowI][colI].setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton button = event.getButton();


//

                        //with flags when its set you set it to f if not visible and unflag set text to orginal value might need old printall
                        if(button==MouseButton.PRIMARY){
                            System.out.println(GridPane.getRowIndex((Node) event.getSource()));
                            int rowIndex=GridPane.getRowIndex((Node) event.getSource());
                            int colIndex=GridPane.getColumnIndex((Node) event.getSource());
                            if(isFirstClick){
                                g.firstMoveSetup(rowIndex,colIndex);
                                isFirstClick=false;
                            }else {
                                g.setTilesVisible(rowIndex,colIndex);


                            }
                        }else if(button==MouseButton.SECONDARY){
                            int rowIndex=GridPane.getRowIndex((Node) event.getSource());
                            int colIndex=GridPane.getColumnIndex((Node) event.getSource());
                            g.flag(rowIndex,colIndex);

                        }
                        System.out.println(g.gameWon());
                    }
                });
                gridPane.add(tempGrid[rowI][colI],colI,rowI,1,1);
            }


        }




        Scene scene = new Scene(gridPane, col*22, row*22);
        stage.setScene(scene);
        stage.show();
    }    public void rowColInputPage(Stage stage){
        TextField row=new TextField();
        TextField col=new TextField();
        Button play=new Button("play");
        play.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
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
                       //start game page

                    }catch (NumberFormatException e){
                        Alert notAnumb = new Alert(Alert.AlertType.WARNING);
                        notAnumb.setContentText("please enter valid integers");

                        notAnumb.show();


                    }
                    catch (Exception e){

                        if(e.toString().contains("Not_in_range")){
                            Alert notInRange = new Alert(Alert.AlertType.WARNING);
                            notInRange.setContentText("please enter valid integers between 5 -29");

                            notInRange.show();
                        }

                    }


                }

            }
        });

        GridPane gridPane = new GridPane();

        gridPane.add(new Label("row"),1,1,1,1);
        gridPane.add(row,2,1,1,1);

        gridPane.add(new Label("col"),1,2,1,1);
        gridPane.add(col,2,2,1,1);
        gridPane.add(play,3,3,1,1);
        Scene scene =new Scene(gridPane, 240, 100);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}