package com.example.project_champitheque.Game.ChampiMerge;

public class Shuffle {

    private boolean used;

    protected boolean getUsed() {
        return used;
    }

    public Shuffle() {
        this.used = false;

    }

    public boolean useShuffle(GrilleChampiMerge grille){
        if(!used){
            grille.shuffle();
            return true;
        }
        return false;
    }
}
