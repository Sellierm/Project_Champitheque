package com.example.project_champitheque.Game.MushMiner;

public class Loupe {

    private boolean used;
    protected boolean getUsed(){return used;}

    public Loupe(){
        this.used = false;

    }

    public boolean useLoupe(GrilleMushMiner plateau, int x, int y){
        if(!this.used){
            //X e y vérifiés dans la fonction revealCase
            plateau.revealCase(x-1, y-1);
            plateau.revealCase(x, y-1);
            plateau.revealCase(x+1, y-1);
            plateau.revealCase(x-1, y);
            plateau.revealCase(x+1, y);
            plateau.revealCase(x-1, y+1);
            plateau.revealCase(x, y+1);
            plateau.revealCase(x+1, y+1);
            this.used = true;
            return true;
        }
        else {
            return false;
        }
    }
}
