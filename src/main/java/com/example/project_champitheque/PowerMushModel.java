package com.example.project_champitheque;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class PowerMushModel {

    private ArrayList<ArrayList> grille = new ArrayList<ArrayList>();
    public ArrayList<ArrayList> getGrille() {
        return grille;
    }


    private List<Integer> lastYPlay;

    private List<Integer> lastXPlay;

    private boolean end = false;


    private int sizeChampiWin = 0;

    private List<Integer[]> listCooChampiWin;

    public List<Integer[]> getListCooChampiWin(){
        return listCooChampiWin;
    }

    private int winner = 0;


    public int getJoueurCourant() {
        return joueurCourant;
    }
    private int joueurCourant = 0;
    private int dernierJoueurCourant = 0;
    private int joueur1 = 0;
    private int joueur2 = 0;


    private int nbChampiWin = 6;

    private int sizeX = 11;
    private int sizeY = 8;

    private int difficulty = 1;

    public boolean setDifficulty(int difficulty){
        //On change la difficulté si la parti n'a pas commencé
        if(compteCasesVide() == sizeX * sizeY || end == true) {
            System.out.println("Difficulty set to "+this.difficulty);
            this.difficulty = difficulty;
            return true;
        }
        else {
            return false;
        }
    }



    public PowerMushModel(int joueur1, int joueur2){
        lastXPlay = new ArrayList<>();
        lastYPlay = new ArrayList<>();
        listCooChampiWin = new ArrayList<>();
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        joueurCourant = joueur1;
        dernierJoueurCourant = joueur2;
        setGrille();
        System.out.println(grille);

    }

    public void restartGame(int difficulty, int joueur1, int joueur2){
        lastXPlay.clear();
        lastYPlay.clear();
        sizeChampiWin = 0;
        listCooChampiWin.clear();
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        joueurCourant = joueur1;
        dernierJoueurCourant = joueur2;
        winner = 0;
        this.difficulty = difficulty;
        end = false;
        setGrille();
    }


    public boolean isGameEnd(){
        return (end || compteCasesVide() <= 0);
    }


    public void setGrille(){
        //On clear pour les nouveaux appels à la fonction pour ne pas ajouter des cases à celles déjà existantes

        grille.clear();
        for (int x = 0; x < sizeX; x++) {
            ArrayList<Integer> tmpY = new ArrayList<Integer>();
            for (int y = 0; y < sizeY; y++) {

                tmpY.add(0);
            }
            grille.add(tmpY);
        }
    }

    public int play(int x, int joueur){
        if(x < sizeX && x >= 0) {
            if (!end && compteCasesVide() > 0) {
                if(joueur == joueurCourant) {
                    if (joueur == -1) {
                        botPlay();
                    } else {
                        placeItem(x, joueur);
                    }
                    if (checkWinner(joueurCourant)) {
                        System.out.println("Quelqu'un a gagné");
                        return joueur;
                    } else if (compteCasesVide() > 0) {
                        int tmpJoueur = joueurCourant;
                        joueurCourant = dernierJoueurCourant;
                        dernierJoueurCourant = tmpJoueur;
                        return 0;
                    } else {
                        end = true;
                        System.out.println("Partie finie sans gagnant");
                        return 3;
                    }
                }else {
                    System.out.println("Ce n'est pas à vous de jouer");
                    return 0;
                }
            } else {
                System.out.println("Partie finie");
                return 0;
            }
        } else {
            System.out.println("Colone invalide");
            return 0;
        }
    }


    public boolean placeItem(int x, int player){
        List<Integer> y = grille.get(x);
        int indexY = y.size() - 1;
        while(indexY >= 0 && y.get(indexY) != 0){
            indexY--;
        }
        if(indexY < 0){
            System.out.println("Colone pleine");
            return false;
        }
        else{
            y.set(indexY, player);
            lastYPlay.add(0, indexY);
            lastXPlay.add(0, x);
            return true;
        }
    }

    public boolean checkWinner(int player){
        System.out.println("Hallo on check si y a un gagnant");
        boolean win = false;
        List<Integer[]> cooLignes = new ArrayList<>();
        int yMax = sizeY - 1;
        int yMin = 0;
        int xMax = sizeX - 1;
        int xMin = 0;
        int y = lastYPlay.get(0);
        int x = lastXPlay.get(0);

        System.out.println("On vérifie le coup : "+x+", "+y);


        int compteChampi = 0;
        int tmpX = x;
        int tmpY = y;
        cooLignes.clear();
        cooLignes.add(new Integer[]{tmpX, tmpY});

        //On vérifie vers le bas
        if(!win){
            System.out.println("On vérifie vers le bas");
            compteChampi = 0;
            while(tmpY <= yMax && (int)grille.get(tmpX).get(tmpY) == player) {
                System.out.println("On descend en y = " + tmpY + " avec la valeur : " + (int) grille.get(tmpX).get(tmpY));
                tmpY++;
                compteChampi++;

                if(tmpY <= yMax)cooLignes.add(new Integer[]{tmpX, tmpY});
            }
            if(compteChampi >= nbChampiWin){
                win = true;
            }
        }

        //On vérifie sur les x
        if(!win){
            System.out.println("On vérifie horizontalement");
            compteChampi = 0;
            tmpX = x;
            tmpY = y;
            cooLignes.clear();
            cooLignes.add(new Integer[]{tmpX, tmpY});

            while(tmpX <= xMax && (int)grille.get(tmpX).get(tmpY) == player){
                System.out.println("On avance en x = " + tmpX + " avec la valeur : " + (int)grille.get(tmpX).get(tmpY));
                tmpX++;
                compteChampi++;
            }

            for(int i = x; i < tmpX; i++){
                cooLignes.add(new Integer[]{i, y});
            }

            tmpX = x;
            while(tmpX >= 0 && (int)grille.get(tmpX).get(tmpY) == player){
                System.out.println("On avance en x = " + tmpX + " avec la valeur : " + (int)grille.get(tmpX).get(tmpY));
                tmpX--;
                compteChampi++;
            }

            for(int i = x - 1; i > tmpX; i--){
                cooLignes.add(new Integer[]{i, y});
            }
            //On enlève 1 pour la case cliqué que l'on compte 2 fois
            if(compteChampi - 1 >= nbChampiWin){
                win = true;
            }
        }

        //On vérifie en diagonale
        if(!win){
            compteChampi = 0;
            tmpX = x;
            tmpY = y;
            cooLignes.clear();
            cooLignes.add(new Integer[]{tmpX, tmpY});

            while(tmpX <= xMax && tmpY <= yMax && (int)grille.get(tmpX).get(tmpY) == player){
                tmpX++;
                tmpY++;
                compteChampi++;

            }

            for(int i = x, j = y; i < tmpX && j < tmpY; i++, j++){
                cooLignes.add(new Integer[]{i, j});
            }

            tmpX = x;
            tmpY = y;
            while(tmpX >= 0 && tmpY >= 0 && (int)grille.get(tmpX).get(tmpY) == player){
                tmpX--;
                tmpY--;
                compteChampi++;
            }

            for(int i = x - 1, j = y-1; i > tmpX && j > tmpY; i--, j--){
                cooLignes.add(new Integer[]{i, j});
            }

            //On enlève 1 pour la case cliqué que l'on compte 2 fois
            if(compteChampi - 1 >= nbChampiWin){
                win = true;
            }
        }


        if(!win){
            compteChampi = 0;
            tmpX = x;
            tmpY = y;
            cooLignes.clear();
            cooLignes.add(new Integer[]{tmpX, tmpY});

            while(tmpX >= 0 && tmpY <= yMax && (int)grille.get(tmpX).get(tmpY) == player){
                tmpX--;
                tmpY++;
                compteChampi++;
            }

            for(int i = x - 1, j = y; i > tmpX && j < tmpY; i--, j++){
                cooLignes.add(new Integer[]{i, j+1});
            }

            tmpX = x;
            tmpY = y;
            while(tmpX <= xMax && tmpY >= 0 && (int)grille.get(tmpX).get(tmpY) == player){
                tmpX++;
                tmpY--;
                compteChampi++;
            }

            for(int i = x, j = y-1; i < tmpX && j > tmpY; i++, j--){
                cooLignes.add(new Integer[]{i + 1, j});
            }

            //On enlève 1 pour la case cliqué que l'on compte 2 fois
            if(compteChampi - 1 >= nbChampiWin){
                win = true;
            }
        }

        if(win)winner=player;
        if(win)sizeChampiWin = compteChampi;
        listCooChampiWin = cooLignes;
        end = win;
        return win;
    }

    public int compteCasesVide(){
        int compt = 0;
        for(List<Integer> x : grille){
            for(int y : x){
                if(y == 0) compt++;
            }
        }
        return compt;
    }


    public void botPlay(){
        //Comportement en fonction des difficultés

        Random rand = new Random();

        if(Math.random() <= 0.4 && lastYPlay.get(0) > 0){
            placeItem(lastXPlay.get(0), -1);
            System.out.println("Robot joue au dessus du dernier coup : " + lastXPlay);
        }
        else {

            //Random colone à ajouter
            int minX = 0;
            int maxX = sizeX - 1;
            int randX = rand.nextInt(maxX - minX + 1) + minX;
            while (!placeItem(randX, -1) && compteCasesVide() > 0) {
                System.out.println("Colone invalide (pleine) : " + randX);
                randX = rand.nextInt(maxX - minX + 1) + minX;
            }
            System.out.println("Robot à joué en : " + randX);
        }
    }


    public void playPanier(int x){
        if(x < sizeX && x >= 0 && !end) {
            List<Integer> y = grille.get(x);
            for(int indexY = sizeY - 1; indexY > 1; indexY--){
                y.set(indexY, y.get(indexY-2));
            }
            y.set(0, 0);
            y.set(1, 0);
        }

    }


    public int getScore(){
        int score = 0;
        if(end) {
            System.out.println(score);
            if(winner == 1)score = (this.sizeChampiWin * this.difficulty)*10 / this.compteCasesVide();
            if(winner == -1)score = 0;
            if(winner == 0)score = this.difficulty*2;
        }
        return score;
    }





}
