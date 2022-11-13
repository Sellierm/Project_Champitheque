package com.example.project_champitheque;



import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameModel {

    public ArrayList<ArrayList> grille = new ArrayList<ArrayList>();


    public ArrayList<ArrayList> getGrille(){
        return grille;
    }


    public GameModel(int sizeX, int sizeY, double difficulty){
        setGrille(sizeX, sizeY, difficulty);
        System.out.println(grille);

    }


    public void setGrille(int sizeX, int sizeY, double difficulty){
        //On clear pour les nouveaux appels à la fonction pour ne pas ajouter des cases à celles déjà existantes
        grille.clear();
        for (int y = 0; y < sizeY; y++) {
            ArrayList<Integer> tmpX = new ArrayList<Integer>();
            for (int x = 0; x < sizeX; x++) {
                double value = Math.random();
                int is_bombe = 0;
                if(value < difficulty){
                    is_bombe = 1;
                }
                tmpX.add(is_bombe);
            }
            grille.add(tmpX);
        }
    }


    public int revealCase(int x, int y){

        int valueCase = (int)grille.get(y).get(x);
        System.out.println(valueCase);
        return valueCase;
    }


    public int getNbBombsAround(int x, int y){
        int valueCase = (int)grille.get(y).get(x);
        //Générer une erreur en cas d'appel pour une case avec bombe
        if(valueCase != 1){
            int result = 0;
            int maxYSize = grille.size() - 1;
            int maxXSize = grille.get(y).size() - 1;
            if(y > 0 && y < maxYSize && x > 0 && x < maxXSize){
                result = (int)grille.get(y-1).get(x-1) + (int)grille.get(y-1).get(x) + (int)grille.get(y-1).get(x+1)
                        + (int)grille.get(y).get(x-1) + (int)grille.get(y).get(x+1)
                        + (int)grille.get(y+1).get(x-1) + (int)grille.get(y+1).get(x) + (int)grille.get(y+1).get(x+1);
            }
            else if (y == 0 && y < maxYSize && x > 0 && x < maxXSize) {
                result = (int)grille.get(y).get(x-1) + (int)grille.get(y).get(x+1)
                        + (int)grille.get(y+1).get(x-1) + (int)grille.get(y+1).get(x) + (int)grille.get(y+1).get(x+1);
            }
            else if(y > 0 && y == maxYSize && x > 0 && x < maxXSize){
                result = (int)grille.get(y-1).get(x-1) + (int)grille.get(y-1).get(x) + (int)grille.get(y-1).get(x+1)
                        + (int)grille.get(y).get(x-1) + (int)grille.get(y).get(x+1);
            }
            else if(y > 0 && y < maxYSize && x == 0 && x < maxXSize){
                result = (int)grille.get(y-1).get(x) + (int)grille.get(y-1).get(x+1)
                        + (int)grille.get(y).get(x+1)
                        + (int)grille.get(y+1).get(x) + (int)grille.get(y+1).get(x+1);
            }
            else if(y > 0 && y < maxYSize && x > 0 && x == maxXSize){
                result = (int)grille.get(y-1).get(x-1) + (int)grille.get(y-1).get(x)
                        + (int)grille.get(y).get(x-1)
                        + (int)grille.get(y+1).get(x-1);
            }
            // Les coins
            else if (y == 0 && y < maxYSize && x == 0 && x < maxXSize) {
                result = (int)grille.get(y).get(x+1)
                        + (int)grille.get(y+1).get(x) + (int)grille.get(y+1).get(x+1);
            }
            else if(y > 0 && y == maxYSize && x == 0 && x < maxXSize){
                result = (int)grille.get(y-1).get(x) + (int)grille.get(y-1).get(x+1)
                        + (int)grille.get(y).get(x+1);
            }
            else if(y == 0 && y < maxYSize && x > 0 && x == maxXSize){
                result = (int)grille.get(y).get(x-1)
                        + (int)grille.get(y+1).get(x-1) + (int)grille.get(y+1).get(x);
            }
            else if(y > 0 && y == maxYSize && x > 0 && x == maxXSize){
                result = (int)grille.get(y-1).get(x-1) + (int)grille.get(y-1).get(x)
                        + (int)grille.get(y).get(x-1);
            }
            return result;
        }
        else {
            System.out.println("Appel inccorect, case piégée");
            return 0;
        }
    }


}
