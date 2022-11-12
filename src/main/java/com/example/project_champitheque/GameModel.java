package com.example.project_champitheque;



import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameModel {

    public ArrayList<ArrayList> grille = new ArrayList<ArrayList>();


    public GameModel(int sizeX, int sizeY){
        for (int y = 0; y < sizeY; y++) {
            ArrayList<Integer> tmpX = new ArrayList<Integer>();
            for (int x = 0; x < sizeX; x++) {
                double value = Math.random();
                int is_bombe = 0;
                if(value < 0.2){
                    is_bombe = 1;
                }
                tmpX.add(is_bombe);
            }
            grille.add(tmpX);
        }
        System.out.println(grille);

    }

    public ArrayList<ArrayList> getGrille(){
        return grille;
    }

}
