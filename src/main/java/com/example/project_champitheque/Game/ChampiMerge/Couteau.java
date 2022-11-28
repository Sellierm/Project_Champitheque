package com.example.project_champitheque.Game.ChampiMerge;

public class Couteau {

    private boolean used;

    protected boolean getUsed() {
        return used;
    }

    public Couteau() {
        this.used = false;

    }

    public boolean useCouteau(int x, int y, GrilleChampiMerge grille){
        if(!used){
            if(grille.divide(x, y)){
                this.used = true;
                return true;
            }
            return false;
        }
        return false;
    }
}
