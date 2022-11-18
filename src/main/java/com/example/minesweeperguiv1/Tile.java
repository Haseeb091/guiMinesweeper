package com.example.minesweeperguiv1;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends Button {

    private boolean isVisible=false;
    private boolean isMine=false;

    private int value;


    private boolean isFlagged=false;

    public Tile(int value){
        this.value=value;
        this.setStyle("-fx-text-fill: transparent; -fx-font-size: 10px;");
        this.setText(value+"");
        this.setMinSize(20,20);
        this.setMaxSize(20,20);
        this.setPadding(Insets.EMPTY);//removes all padding

    }

    public void setValue(int value){

        this.value=value;
        this.setText(value+"");



    }

    public int getValue(){
        return value;

    }

    public void setisFlagged(boolean isFlagged){
        if(!isFlagged){
            this.isFlagged=isFlagged;
            this.setGraphic(null);
//            this.setStyle("-fx-text-fill: transparent; -fx-font-size: 10px;");
//            if(isMine){
//
//                this.setText("m");
//
//            } else  {
//                this.setText(value+"");
//
//            }

        }else {

            this.isFlagged=isFlagged;
//            this.setStyle("-fx-text-fill: black; -fx-font-size: 10px;");
//            this.setText("f");

            Image img = new Image("icons8-flag-filled-48.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(20);// don't change size or will have to change in constructor
            view.setFitHeight(20);
            view.setPreserveRatio(true);


            this.setGraphic(view);
        }


    }
    public boolean getisFlagged(){
        return isFlagged;

    }

    public void setisVisible(boolean isVisible){


        if(isVisible){
            if(isMine){
                Image img = new Image("icons8-naval-mine-48.png");
                ImageView view = new ImageView(img);
                view.setFitHeight(20);
                view.setFitHeight(20);
                view.setPreserveRatio(true);
                this.setText("");

                this.setGraphic(view);



            }else {
                System.out.println(this.getText());
                this.isVisible=isVisible;
                this.setStyle("-fx-text-fill: black; -fx-font-size: 10px;");

            }


        }else {
            this.setStyle("-fx-text-fill: transparent; -fx-font-size: 10px;");// making the text transparent
            this.isVisible=isVisible;

        }


    }
    public boolean getisVisible(){

        return isVisible;
    }

    public void setisMine(boolean isMine){
        if(isMine){
            this.isMine=isMine;
            this.setText("m");

        }

    }
    public boolean getisMine(){
        return isMine;

    }



}
