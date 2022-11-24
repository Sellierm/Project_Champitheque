package com.example.project_champitheque.ChampiMerge;

import com.example.project_champitheque.GameModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

public class ChampiMergeModel extends GameModel {

    public int calculScore(){
        return getScoreMerge();
    }

    public String getFileToWriteStats(){
        return "ChampiMergeScores";
    }
    private GrilleChampiMerge grille;

    private final IntegerProperty scoreMerge = new SimpleIntegerProperty();
    public IntegerProperty scoreMergeProperty() { return scoreMerge; }
    public int getScoreMerge() {
        return scoreMerge.get();
    }

    public ChampiMergeModel(int size){
        startGame(size);
    }

    public void resetGame(int size){
        startGame(size);
    }

    private void startGame(int size){

        grille = new GrilleChampiMerge(size);

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
        scoreMerge.setValue(grille.getScore());
    }
}
