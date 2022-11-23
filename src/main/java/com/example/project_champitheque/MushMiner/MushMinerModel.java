package com.example.project_champitheque.MushMiner;

import com.example.project_champitheque.Interfaces.EndGame;
import com.example.project_champitheque.fileManager.Write;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

public class MushMinerModel implements EndGame {


    private Grille plateau;

    private Loupe loupe;

    private Engrais engrais;

    private final IntegerProperty coupsRestants = new SimpleIntegerProperty();
    public IntegerProperty coupsRestantsProperty() {
        return coupsRestants;
    }

    private final IntegerProperty scoreChampi = new SimpleIntegerProperty();
    public IntegerProperty scoreChampiProperty() {
        return scoreChampi;
    }

    private int casesChampiTrouves;

    private final IntegerProperty champiRestants = new SimpleIntegerProperty();
    public IntegerProperty champiRestantsProperty() {
        return champiRestants;
    }

    private int coupsJoues;

    private int nbCoupsMax;

    private int score;
    public int getScore(){return this.score;}

    private int difficulty = 1;
    public void setDifficulty(int difficulty) {
        if(difficulty > 0 || difficulty <= 3){
            this.difficulty = difficulty;
        }
        else {
            this.difficulty = 1;
        }
        System.out.println("Difficulty set to : "+this.difficulty);
    }

    private boolean gameEnd;


    public void setGameEnd(){
        this.gameEnd = true;
        this.score = (int)(this.scoreChampi.get() * (this.difficulty * (this.difficulty+1)) + (this.coupsRestants.get()/2));
        Write writer = new Write();
        writer.writeScore(score, "MushMinerScores");
    }
    public void resetGameEnd(){
        this.gameEnd = false;
        this.score = 0;
    }

    public MushMinerModel(int sizeX, int sizeY){
        startGame(sizeX, sizeY);
    }

    public void resetGame(int sizeX, int sizeY){
        startGame(sizeX, sizeY);
    }

    private void startGame(int sizeX, int sizeY){

        plateau = new Grille(sizeX, sizeY, this.difficulty);


        switch (this.difficulty){
            case 1:
                this.coupsRestants.setValue(20);
                break;
            case 2:
                this.coupsRestants.setValue(25);
                break;
            case 3:
                this.coupsRestants.setValue(30);
                break;
            default:
                break;
        }

        coupsJoues = 0;
        nbCoupsMax = sizeX*sizeY;
        casesChampiTrouves = 0;
        loupe = new Loupe();
        engrais = new Engrais();

        scoreChampi.setValue(0);
        champiRestants.setValue(plateau.getChampiToDiscover());

        resetGameEnd();
    }


    public void play(int x, int y){
        if(!gameEnd){
            if(!plateau.isRevealedCase(x, y)) {
                ValueCase result = plateau.revealCase(x, y);
                if (result == ValueCase.BAD) {
                    coupsRestants.setValue(coupsRestants.get() - 4);
                }
                if (result == ValueCase.JACKPOT) {
                    scoreChampi.setValue(scoreChampi.get() + 5);
                }
                if (result == ValueCase.CHAMPI) {
                    scoreChampi.setValue(scoreChampi.get() + 1);
                }
                if(result == ValueCase.VIDE){
                    coupsRestants.setValue(coupsRestants.get() - 1);
                }

                updateValuesGame();
            }
        }
    }

    public void lockCase(int x, int y){
        plateau.lockCase(x, y);
    }


    public List<List<CaseToDisplay>> getGrilleToDisplay(){
        return plateau.showGrille();
    }

    public boolean isGameEnd(){
        return gameEnd;
    }


    //Bonus
    public boolean playLoupe(int x, int y){
        return loupe.useLoupe(plateau, x, y);
    }
    public boolean loupeAvaible(){
        return !loupe.getUsed();
    }

    public boolean playEngrais(int x, int y){
        return engrais.useEngrais(plateau, x, y);
    }
    public boolean engraisAvaible(){
        return !engrais.getUsed();
    }

    //Vérification de l'état du jeu
    public void updateValuesGame(){
        coupsJoues++;
        if(coupsRestants.get() < 0)coupsRestants.setValue(0);
        casesChampiTrouves = plateau.getChampiDiscovered();
        champiRestants.setValue(plateau.getChampiToDiscover());
        if(this.coupsRestants.get() <= 0 || coupsJoues > nbCoupsMax || champiRestants.get() <= 0)setGameEnd();
        System.out.println("Coups joués : "+coupsJoues+", coups restants : "+coupsRestants.get()+ ", coups max : "+ nbCoupsMax +", champi restants : "+champiRestants.get());
    }

    public List<List<CaseToDisplay>> getGrilleEnd(){
        if(isGameEnd()){
            return plateau.showGrilleEnd();
        }
        return new ArrayList<>();
    }


    public void increaseRestant(boolean grow){
        if(grow) {
            this.coupsRestants.setValue(this.coupsRestants.get() + 5);
        }
        else {
            this.coupsRestants.setValue(this.coupsRestants.get() - 5);
        }
    }

}
