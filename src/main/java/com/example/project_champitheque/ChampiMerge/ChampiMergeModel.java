package com.example.project_champitheque.ChampiMerge;

import com.example.project_champitheque.Interfaces.EndGame;
import com.example.project_champitheque.fileManager.Write;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

public class ChampiMergeModel implements EndGame {

    private Grid grille;

    private boolean gameEnd;

    public void setGameEnd(){
        this.gameEnd = true;
        Write writer = new Write();
        writer.writeScore(score.get(), "ChampiMergeScores");
    }
    public void resetGameEnd(){
        this.gameEnd = false;
        this.score.setValue(0);
    }

    private final IntegerProperty score = new SimpleIntegerProperty();
    public IntegerProperty scoreProperty() {
        return score;
    }
    public int getScore() {
        return score.get();
    }

    public ChampiMergeModel(int size){
        startGame(size);
    }

    public void resetGame(int size){
        startGame(size);
    }

    private void startGame(int size){

        grille = new Grid(size);

        resetGameEnd();

    }


    public List<List<Integer>> getGrilleToDisplay(){
        return grille.showGrille();
    }

    public void play(Direction direction) {
        if (!gameEnd) {
            boolean resultCoup = false;
            switch (direction) {
                case UP:
                    resultCoup = grille.goUp();
                    break;
                case DOWN:
                    resultCoup = grille.goDown();
                    break;
                case LEFT:
                    resultCoup = grille.goLeft();
                    break;
                case RIGHT:
                    resultCoup = grille.goRight();
                    break;
            }
            if(resultCoup)setGameEnd();
            updateScore();
        }
    }

    private void updateScore(){
        score.setValue(grille.getScore());
    }

    public boolean isGameEnd(){
        return gameEnd;
    }
}
