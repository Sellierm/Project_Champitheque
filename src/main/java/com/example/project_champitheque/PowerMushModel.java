package com.example.project_champitheque;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    private int nbChampiWin = 6;

    private int sizeX = 11;
    private int sizeY = 8;

    private int difficulty = 1;



    public PowerMushModel(){
        setGrille();
        lastXPlay = new ArrayList<>();
        lastYPlay = new ArrayList<>();
        listCooChampiWin = new ArrayList<>();
        System.out.println(grille);

    }

    public void restartGame(int difficulty){
        setGrille();
        lastXPlay.clear();
        lastYPlay.clear();
        sizeChampiWin = 0;
        listCooChampiWin.clear();
        winner = 0;
        this.difficulty = difficulty;
        end = false;
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

    public int play(int x){
        if(x < sizeX && x >= 0) {
            if (!end){
                if (compteCasesVide() > 0) {
                    placeItem(x, 1);
                    if (compteCasesVide() > 0) {
                        if (!checkWinner(1)) {
                            botPlay();
                            if (checkWinner(-1)) {
                                System.out.println("Robot gagnant");
                                return -1;
                            }
                            else if (compteCasesVide() <= 0) {
                                end = true;
                                System.out.println("Partie finie sans gagnant");
                                return 2;
                            }
                            else{
                                return 0;
                            }
                        } else {
                            System.out.println("Vous avez gagné");
                            return 1;
                        }
                    } else if (checkWinner(1)) {
                        System.out.println("Vous avez gagné");
                        return 1;
                    } else {
                        end = true;
                        System.out.println("Partie finie sans gagnant");
                        return 2;
                    }
                } else {
                    end = true;
                    System.out.println("Partie finie");
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
        boolean win = false;
        List<Integer[]> cooLignes = new ArrayList<>();
        int yMax = sizeY - 1;
        int yMin = 0;
        int xMax = sizeX - 1;
        int xMin = 0;
        int y = lastYPlay.get(0);
        int x = lastXPlay.get(0);


        int compteChampi = 0;
        int tmpX = x;
        int tmpY = y;
        cooLignes.clear();
        cooLignes.add(new Integer[]{tmpX, tmpY});

        //On vérifie vers le bas
        if(!win){
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
            compteChampi = 0;
            tmpX = x;
            tmpY = y;
            cooLignes.clear();
            cooLignes.add(new Integer[]{tmpX, tmpY});

            while(tmpX <= xMax && (int)grille.get(tmpX).get(tmpY) == player){
                System.out.println("On avance en x = " + tmpX + " avec la valeur : " + (int)grille.get(tmpX).get(tmpY));
                tmpX++;
                compteChampi++;

                if(tmpX <= xMax)cooLignes.add(new Integer[]{tmpX, tmpY});
            }
            tmpX = x;
            while(tmpX >= 0 && (int)grille.get(tmpX).get(tmpY) == player){
                System.out.println("On avance en x = " + tmpX + " avec la valeur : " + (int)grille.get(tmpX).get(tmpY));
                tmpX--;
                compteChampi++;

                if(tmpX >= 0)cooLignes.add(new Integer[]{tmpX, tmpY});
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
                System.out.println("On avance en x = " + tmpX + " avec la valeur : " + (int)grille.get(tmpX).get(tmpY));
                tmpX++;
                tmpY++;
                compteChampi++;

                if(tmpX <= xMax && tmpY <= yMax)cooLignes.add(new Integer[]{tmpX, tmpY});
            }
            tmpX = x;
            tmpY = y;
            while(tmpX >= 0 && tmpY >= 0 && (int)grille.get(tmpX).get(tmpY) == player){
                System.out.println("On avance en x = " + tmpX + " avec la valeur : " + (int)grille.get(tmpX).get(tmpY));
                tmpX--;
                tmpY--;
                compteChampi++;

                if(tmpX >= 0 && tmpY >= 0)cooLignes.add(new Integer[]{tmpX, tmpY});
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
                System.out.println("On avance en x = " + tmpX + " avec la valeur : " + (int)grille.get(tmpX).get(tmpY));
                tmpX--;
                tmpY++;
                compteChampi++;

                if(tmpX >= 0 && tmpY <= yMax)cooLignes.add(new Integer[]{tmpX, tmpY});
            }
            tmpX = x;
            tmpY = y;
            while(tmpX <= xMax && tmpY >= 0 && (int)grille.get(tmpX).get(tmpY) == player){
                System.out.println("On avance en x = " + tmpX + " avec la valeur : " + (int)grille.get(tmpX).get(tmpY));
                tmpX++;
                tmpY--;
                compteChampi++;

                if(tmpX <= xMax && tmpY >= 0)cooLignes.add(new Integer[]{tmpX, tmpY});
            }
            //On enlève 1 pour la case cliqué que l'on compte 2 fois
            if(compteChampi - 1 >= nbChampiWin){
                win = true;
            }
        }

        if(win)winner=player;
        if(win)nbChampiWin = compteChampi;
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

        if(Math.random() <= 0.4){
            placeItem(lastXPlay.get(0), -1);
            System.out.println("Robot joue au dessus du dernier coup : " + lastXPlay);
        }
        else {

            //Random colone à ajouter
            int minX = 0;
            int maxX = 6;
            int randX = rand.nextInt(maxX - minX + 1) + minX;
            while (!placeItem(randX, -1) && compteCasesVide() > 0) {
                System.out.println("Colone invalide (pleine) : " + randX);
                randX = rand.nextInt(maxX - minX + 1) + minX;
            }
            System.out.println("Robot à joué en : " + randX);
        }
    }


    public void playPanier(int x){
        if(x < 7 && x >= 0 && !end) {
            List<Integer> y = grille.get(x);
            y.replaceAll(e -> 0);
        }

    }


    public int getScore(){
        int score = 0;
        if(end) {
            System.out.println(score);
            if(winner == 1)score = (this.nbChampiWin * this.difficulty)*10 / this.compteCasesVide();
            if(winner == -1)score = 0;
            if(winner == 0)score = this.difficulty*2;
        }
        return score;
    }





}
