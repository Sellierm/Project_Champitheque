package com.example.project_champitheque.Game.PowerMush;


import com.example.project_champitheque.Game.GameModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerMushModel extends GameModel {

    public int calculScore(){
        int score = 0;
        if(gameEnd) {
            System.out.println(score);
            if(winner == Joueur.JOUEUR1)score = (this.sizeChampiWin * this.difficulty)*10;
            if(winner == Joueur.BOT)score = 0;
            if(winner == Joueur.AUCUN)score = this.difficulty*2;
        }
        return score;
    }

    public String getFileToWriteStats(){
        return "PowerMush";
    }

    private GrillePowerMush grille;

    private Panier panier;

    private Boots boots;


    private int sizeChampiWin = 0;

    private List<Integer[]> listCooChampiWin = new ArrayList<>();

    public List<Integer[]> getListCooChampiWin(){
        return listCooChampiWin;
    }

    private Joueur winner = Joueur.AUCUN;


    public Joueur getJoueurCourant() {
        return joueurCourant;
    }
    private Joueur joueurCourant = Joueur.AUCUN;
    private Joueur dernierJoueurCourant = Joueur.AUCUN;



    private boolean joueur1boots = true;

    private boolean joueur2boots = true;


    private int nbChampiWin = 6;

    private int difficulty = 1;

    private int botDifficulty = 1;

    public void setDifficulty(int difficulty){
        if(difficulty > 0 || difficulty <= 3){
            this.difficulty = difficulty;
        }
        else {
            this.difficulty = 1;
        }
        System.out.println("Difficulty set to : "+this.difficulty);
    }



    //Début de partie
    public PowerMushModel(Joueur joueur1, Joueur joueur2){
        startGame(joueur1, joueur2);
    }

    public void restartGame(Joueur joueur1, Joueur joueur2){
        startGame(joueur1, joueur2);
    }


    private void startGame(Joueur joueur1, Joueur joueur2){

        sizeChampiWin = 0;
        listCooChampiWin.clear();
        joueurCourant = joueur1;
        dernierJoueurCourant = joueur2;
        joueur1boots = true;
        joueur2boots = true;
        winner = Joueur.AUCUN;

        panier = new Panier();
        boots = new Boots();
        grille = new GrillePowerMush();

        botDifficulty = difficulty;

        resetGameEnd();
    }



    //Fonctions de la partie
    public void play(int x, Joueur joueur){
        if(!gameEnd && joueur == joueurCourant) {
            boolean tmp = true;
            if (joueur == Joueur.BOT) {
                botPlay();
            }
            else {
                tmp = grille.placeItem(x, joueur);
            }
            if(tmp){
                boolean verif1 =  checkWinner(joueurCourant, grille.getLastXPlay().get(0), grille.getLastYPlay().get(0));
                boolean verif2 =  checkGrilleFull();
                if (verif1 || verif2) {
                    setGameEnd();
                }
                else {
                    Joueur tmpJoueur = joueurCourant;
                    joueurCourant = dernierJoueurCourant;
                    dernierJoueurCourant = tmpJoueur;
                }
            }
        }

    }

    public ArrayList<ArrayList<Joueur>> getGrilleToDisplay(){
        return grille.getGrille();
    }

    public Joueur getWinner(){return this.winner;}

    //Bonus
    public boolean playPanier(int x){
        if(!gameEnd && panier.ablePanier(joueurCourant)) {
            boolean result = panier.usePanier(x, grille, joueurCourant);

            int y = 0;
            while(!checkWinner(joueurCourant, x, y) && y < grille.getSizeY() - 1){
                y++;
            }
            if(checkWinner(joueurCourant, x, y))setGameEnd();

            return result;
        }
        return false;
    }
    public boolean ablePanier(){
        return panier.ablePanier(joueurCourant);
    }

    public boolean playBoots(int x){
        if(!gameEnd && boots.ableBoots(joueurCourant)) {
            return boots.useBoots(x, grille, joueurCourant);
        }
        return false;
    }
    public boolean ableBoots(){
        return boots.ableBoots(joueurCourant);
    }



    public void botPlay(){
        //Comportement en fonction des difficultés

        Random rand = new Random();
        System.out.println(grille.getLastXPlay());
        System.out.println(grille.getLastYPlay());
        if(this.botDifficulty > 1 && Math.random() <= 0.4 && grille.getLastYPlay().get(0) > 0){
            grille.placeItem(grille.getLastXPlay().get(0), Joueur.BOT);
            System.out.println("Robot (niv2) à joué en : " + grille.getLastXPlay().get(0));
        }
        else if(this.botDifficulty > 2 && Math.random() <= 0.5) {
            if (!grille.placeItem(grille.getLastXPlay().get(0) + 1, Joueur.BOT)){
                if(!grille.placeItem(grille.getLastXPlay().get(0) - 1, Joueur.BOT)){
                    //Random colone à ajouter
                    int minX = 0;
                    int maxX = grille.getSizeX() - 1;
                    int randX = rand.nextInt(maxX - minX + 1) + minX;
                    while (!grille.placeItem(randX, Joueur.BOT) && grille.compteCasesVide() > 0) {
                        System.out.println("Colone invalide (pleine) : " + randX);
                        randX = rand.nextInt(maxX - minX + 1) + minX;
                    }
                    System.out.println("Robot (niv3) à joué en : " + randX);
                }
            }
        }
        else {
            //Random colone à ajouter
            int minX = 0;
            int maxX = grille.getSizeX() - 1;
            int randX = rand.nextInt(maxX - minX + 1) + minX;
            while (!grille.placeItem(randX, Joueur.BOT) && grille.compteCasesVide() > 0) {
                System.out.println("Colone invalide (pleine) : " + randX);
                randX = rand.nextInt(maxX - minX + 1) + minX;
            }
            System.out.println("Robot à joué en : " + randX);
        }
    }

    private boolean checkWinner(Joueur player, int x, int y){
        if(!gameEnd) {
            boolean win = false;
            List<Integer[]> cooLignes = new ArrayList<>();
            int yMax = grille.getSizeY() - 1;
            int xMax = grille.getSizeX() - 1;

            //System.out.println("On vérifie le coup : " + x + ", " + y);


            int compteChampi = 0;
            int tmpX = x;
            int tmpY = y;
            cooLignes.clear();
            cooLignes.add(new Integer[]{tmpX, tmpY});

            //On vérifie vers le bas
            if (!win) {
                System.out.println("On vérifie vers le bas");
                compteChampi = 0;
                while (tmpY <= yMax && grille.getGrille().get(tmpX).get(tmpY) == player) {
                    System.out.println("On descend en y = " + tmpY + " avec la valeur : " + grille.getGrille().get(tmpX).get(tmpY));
                    tmpY++;
                    compteChampi++;

                    if (tmpY <= yMax) cooLignes.add(new Integer[]{tmpX, tmpY});
                }
                if (compteChampi >= nbChampiWin) {
                    win = true;
                }
            }

            //On vérifie sur les x
            if (!win) {
                System.out.println("On vérifie horizontalement");
                compteChampi = 0;
                tmpX = x;
                tmpY = y;
                cooLignes.clear();
                cooLignes.add(new Integer[]{tmpX, tmpY});

                while (tmpX <= xMax && grille.getGrille().get(tmpX).get(tmpY) == player) {
                    System.out.println("On avance en x = " + tmpX + " avec la valeur : " + grille.getGrille().get(tmpX).get(tmpY));
                    tmpX++;
                    compteChampi++;
                }

                for (int i = x; i < tmpX; i++) {
                    cooLignes.add(new Integer[]{i, y});
                }

                tmpX = x;
                while (tmpX >= 0 && grille.getGrille().get(tmpX).get(tmpY) == player) {
                    System.out.println("On avance en x = " + tmpX + " avec la valeur : " + grille.getGrille().get(tmpX).get(tmpY));
                    tmpX--;
                    compteChampi++;
                }

                for (int i = x - 1; i > tmpX; i--) {
                    cooLignes.add(new Integer[]{i, y});
                }
                //On enlève 1 pour la case cliqué que l'on compte 2 fois
                if (compteChampi - 1 >= nbChampiWin) {
                    win = true;
                }
            }

            //On vérifie en diagonale
            if (!win) {
                compteChampi = 0;
                tmpX = x;
                tmpY = y;
                cooLignes.clear();
                cooLignes.add(new Integer[]{tmpX, tmpY});

                while (tmpX <= xMax && tmpY <= yMax && grille.getGrille().get(tmpX).get(tmpY) == player) {
                    tmpX++;
                    tmpY++;
                    compteChampi++;

                }

                for (int i = x, j = y; i < tmpX && j < tmpY; i++, j++) {
                    cooLignes.add(new Integer[]{i, j});
                }

                tmpX = x;
                tmpY = y;
                while (tmpX >= 0 && tmpY >= 0 && grille.getGrille().get(tmpX).get(tmpY) == player) {
                    tmpX--;
                    tmpY--;
                    compteChampi++;
                }

                for (int i = x - 1, j = y - 1; i > tmpX && j > tmpY; i--, j--) {
                    cooLignes.add(new Integer[]{i, j});
                }

                //On enlève 1 pour la case cliqué que l'on compte 2 fois
                if (compteChampi - 1 >= nbChampiWin) {
                    win = true;
                }
            }


            if (!win) {
                compteChampi = 0;
                tmpX = x;
                tmpY = y;
                cooLignes.clear();
                cooLignes.add(new Integer[]{tmpX, tmpY});

                while (tmpX >= 0 && tmpY <= yMax && grille.getGrille().get(tmpX).get(tmpY) == player) {
                    tmpX--;
                    tmpY++;
                    compteChampi++;
                }

                for (int i = x - 1, j = y; i > tmpX && j < tmpY; i--, j++) {
                    cooLignes.add(new Integer[]{i, j + 1});
                }

                tmpX = x;
                tmpY = y;
                while (tmpX <= xMax && tmpY >= 0 && grille.getGrille().get(tmpX).get(tmpY) == player) {
                    tmpX++;
                    tmpY--;
                    compteChampi++;
                }

                for (int i = x, j = y - 1; i < tmpX && j > tmpY; i++, j--) {
                    cooLignes.add(new Integer[]{i + 1, j});
                }

                //On enlève 1 pour la case cliqué que l'on compte 2 fois
                if (compteChampi - 1 >= nbChampiWin) {
                    win = true;
                }
            }

            if (win) {
                winner = player;
                sizeChampiWin = compteChampi;
                listCooChampiWin = cooLignes;
            }
            return win;
        }
        return gameEnd;
    }

    private boolean checkGrilleFull(){
        boolean result = (grille.compteCasesVide() <= 0 && !joueur1boots && !joueur2boots && !panier.isJoueur1panier() && !panier.isJoueur2panier());
        if(result) winner=Joueur.AUCUN;
        return result;
    }

}
