package com.example.project_champitheque.PowerMush;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

public class ChampiMergeModel {

    private Grid grille;

    private boolean end;

    private final IntegerProperty score = new SimpleIntegerProperty();
    public IntegerProperty scoreProperty() {
        return score;
    }

    public ChampiMergeModel(int size){
        startGame(size);
    }

    public void resetGame(int size){
        startGame(size);
    }

    private void startGame(int size){

        grille = new Grid(size);
        end = false;

    }


    public List<List<Integer>> getGrilleToDisplay(){
        return grille.showGrille();
    }

    public void play(Direction direction) {
        if (!end) {
            switch (direction) {
                case UP:
                    grille.goUp();
                    break;
                case DOWN:
                    end = grille.goDown();
                    break;
                case LEFT:
                    grille.goLeft();
                    break;
                case RIGHT:
                    grille.goRight();
                    break;
            }
            updateScore();
        }
    }

    private void updateScore(){
        score.setValue(grille.getScore());
    }

    public boolean isGameEnd(){
        return end;
    }
}
