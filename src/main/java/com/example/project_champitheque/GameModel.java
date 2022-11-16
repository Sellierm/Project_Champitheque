package com.example.project_champitheque;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Random;

public class GameModel {

    public ArrayList<ArrayList> grille = new ArrayList<ArrayList>();

    private final IntegerProperty champiFind;

    private final IntegerProperty restant;

    public int difficulty = 1;
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        System.out.println(this.difficulty);
    }


    public ArrayList<ArrayList> getGrille(){
        return grille;
    }


    public int getChampiFind() {
        return champiFind.get();
    }
    public IntegerProperty champiFindProperty() {
        return champiFind;
    }
    public int getRestant() {
        return restant.get();
    }

    public IntegerProperty restantProperty() {
        return restant;
    }


    public GameModel(int sizeX, int sizeY){
        this.champiFind = new SimpleIntegerProperty(0);
        this.restant = new SimpleIntegerProperty(20);
        this.difficulty = difficulty;
        setGrille(sizeX, sizeY);
        System.out.println(grille);

    }

    public void restartGame(int sizeX, int sizeY){
        setGrille(sizeX, sizeY);
        champiFind.setValue(0);
    }


    public void setGrille(int sizeX, int sizeY){
        double randDifficulty = 0.9;
        switch (this.difficulty){
            case 1:
                randDifficulty = 0.6;
                this.restant.setValue(20);
                break;
            case 2:
                randDifficulty = 0.4;
                this.restant.setValue(25);
                break;
            case 3:
                randDifficulty = 0.2;
                this.restant.setValue(30);
                break;
            default:
                break;
        }
        //On clear pour les nouveaux appels à la fonction pour ne pas ajouter des cases à celles déjà existantes
        grille.clear();
        for (int y = 0; y < sizeY; y++) {
            ArrayList<Integer> tmpX = new ArrayList<Integer>();
            for (int x = 0; x < sizeX; x++) {
                double value = Math.random();
                int is_bombe = 0;
                if(value < randDifficulty){
                    is_bombe = 1;
                }
                tmpX.add(is_bombe);
            }
            grille.add(tmpX);
        }
    }


    public int revealCase(int x, int y){
        int valueCase = (int) grille.get(y).get(x);
        System.out.println(restant);
        System.out.println(champiFind);
        if(restant.get() > 0) {
            System.out.println(valueCase);
            if (valueCase == 1) {
                champiFind.setValue(champiFind.get()+1);
            }
            else{
                restant.setValue(restant.get()-1);
            }
        }
        else{
            System.out.println("Vous êtes à cours de coups");
        }
        return valueCase;
    }


    public int getCaseValue(int x, int y){
        int maxYSize = grille.size() - 1;
        int maxXSize = grille.get(y).size() - 1;
        if(y >= 0 && y <= maxYSize && x >= 0 && x <= maxXSize) {
            return (int) grille.get(y).get(x);
        }
        else {
            return 0;
        }
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

    // Calcul du score
    public int finalScore(){
        //Score calculé en fonction de la difficulté, et des champignons trouvés, + le nombre de tours restants divisé par 2
        int score =  (int)(this.getChampiFind() * (this.difficulty * (this.difficulty+1)) + (this.getRestant()/2));
        System.out.println(score);
        return score;
    }



    //CheatCode
    public void increaseRestant(boolean grow){
        if(grow) {
            this.restant.setValue(this.restant.get() + 5);
        }
        else {
            this.restant.setValue(this.restant.get() - 5);
        }
    }


    public void setManure(int x, int y){
        int min = -1;
        int max = 1;
        Random rand = new Random();
        int nombreAleatoire = rand.nextInt(max - min + 1) + min;

        int minNbChampi = -1;
        int maxNbChampi = 1;
        int randNbChampi = rand.nextInt(maxNbChampi - minNbChampi + 1) + minNbChampi;
        for (int i = 0; )
        System.out.println("Test nombre aléatoire");
        System.out.println(nombreAleatoire);
    }


}
