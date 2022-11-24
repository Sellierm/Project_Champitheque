package com.example.project_champitheque.PowerMush;

import java.util.List;

public class Boots {


    public Boots(){

    }

    private boolean joueur1boots = true;
    public boolean isJoueur1boots() {
        return joueur1boots;
    }

    private boolean joueur2boots = true;
    public boolean isJoueur2boots() {
        return joueur2boots;
    }

    public boolean useBoots(int x, GrillePowerMush grille, Joueur joueurCourant){
        if(x < grille.getSizeX() && x >= 0) {
            List<Joueur> y = grille.getGrille().get(x);
            int indexY = 0;
            while(y.get(indexY) == Joueur.AUCUN){
                indexY++;
            }
            y.set(indexY, Joueur.AUCUN);
            y.set(indexY+1, Joueur.AUCUN);

            if(joueurCourant == Joueur.JOUEUR1){
                joueur1boots = false;
            }
            if(joueurCourant == Joueur.JOUEUR2) {
                joueur2boots = false;
            }
            return true;
        }
        return false;
    }

    protected boolean ableBoots(Joueur joueurCourant){
        return (joueurCourant == Joueur.JOUEUR1 && joueur1boots) || (joueurCourant == Joueur.JOUEUR2 && joueur2boots);
    }
}
