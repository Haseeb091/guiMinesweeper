package com.example.minesweeperguiv1;


import javafx.scene.control.Button;

public class Tile extends Button {

    private boolean isVisible=false;
    private boolean isMine=false;

    private int value;


    private boolean isFlagged=false;

    public Tile(int value){
        this.value=value;
        this.setStyle("-fx-text-fill: transparent; -fx-font-size: 10px;");
        this.setText(value+"");

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
            this.setStyle("-fx-text-fill: transparent; -fx-font-size: 10px;");
            if(isMine){

                this.setText("m");

            } else  {
                this.setText(value+"");

            }

        }else {

            this.isFlagged=isFlagged;
            this.setStyle("-fx-text-fill: black; -fx-font-size: 10px;");
            this.setText("f");
        }


    }
    public boolean getisFlagged(){
        return isFlagged;

    }

    public void setisVisible(boolean isVisible){
        if(isVisible){
            this.isVisible=isVisible;
            this.setStyle("-fx-text-fill: black; -fx-font-size: 10px;");

        }else {
            this.setStyle("-fx-text-fill: transparent; -fx-font-size: 10px;");
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

    public void print(){
        if(!isVisible){
            if (isFlagged){
                System.out.print("f");
            }else {
                System.out.print("x");}
        }else{
            if(isMine){
                System.out.print("M");

            }else {
                System.out.print(value);

            }

        }

    }

}
