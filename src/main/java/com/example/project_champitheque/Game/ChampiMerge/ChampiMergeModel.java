package com.example.project_champitheque.Game.ChampiMerge;

import com.example.project_champitheque.Game.GameModel;
import com.example.project_champitheque.Game.MushMiner.Engrais;
import com.example.project_champitheque.Game.MushMiner.Loupe;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

public class ChampiMergeModel extends GameModel {

    public int calculScore(){
        return getScoreMerge()/(int)Math.pow(size, 2);
    }

    public String getFileToWriteStats(){
        return "ChampiMerge";
    }
    private GrilleChampiMerge grille;

    private Couteau couteau;

    private Shuffle shuffle;

    private int size;

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

        this.size = size;

        grille = new GrilleChampiMerge(size);


        couteau = new Couteau();
        shuffle = new Shuffle();

        resetGameEnd();

    }


    public List<List<Long>> getGrilleToDisplay(){
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


    //Bonus
    public boolean playCouteau(int x, int y){
        return couteau.useCouteau(x, y, grille);
    }
    public boolean couteauAvaible(){
        return !couteau.getUsed();
    }
    public boolean playShuffle(){
        return shuffle.useShuffle(grille);
    }
    public boolean shuffleAvaible(){
        return !shuffle.getUsed();
    }

    private void updateScore(){
        scoreMerge.setValue(grille.getScore());
    }
}
