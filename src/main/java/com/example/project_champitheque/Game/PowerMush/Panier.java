package com.example.project_champitheque.Game.PowerMush;

import java.util.List;

public class Panier {

    public Panier(){
    }

    private boolean joueur1panier = true;
    public boolean isJoueur1panier() {
        return joueur1panier;
    }

    private boolean joueur2panier = true;
    public boolean isJoueur2panier() {
        return joueur2panier;
    }

    protected boolean usePanier(int x, GrillePowerMush grille, Joueur joueurCourant){
        if (x < grille.getSizeX() && x >= 0) {
            List<Joueur> y = grille.getGrille().get(x);
            for (int indexY = grille.getSizeY() - 1; indexY > 1; indexY--) {
                y.set(indexY, y.get(indexY - 2));
                //Vérification de victoire avec les pions qui tombent
                System.out.println("Vérification victoire panier en y = " + indexY);
                //checkWinner(joueurCourant, x, indexY);
            }
            y.set(0, Joueur.AUCUN);
            y.set(1, Joueur.AUCUN);

            if (joueurCourant == Joueur.JOUEUR1) {
                joueur1panier = false;
            }
            if (joueurCourant == Joueur.JOUEUR2) {
                joueur2panier = false;
            }
            return true;
        }
        return false;
    }

    protected boolean ablePanier(Joueur joueurCourant){
        return (joueurCourant == Joueur.JOUEUR1 && joueur1panier) || (joueurCourant == Joueur.JOUEUR2 && joueur2panier);
    }
}
