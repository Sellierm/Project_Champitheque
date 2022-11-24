package com.example.project_champitheque.PowerMush;

import java.util.ArrayList;
import java.util.List;

public class GrillePowerMush {
    private ArrayList<ArrayList<Joueur>> grille;
    public ArrayList<ArrayList<Joueur>> getGrille() {
        return this.grille;
    }

    private List<Integer> lastYPlay;
    public List<Integer> getLastYPlay() {
        return lastYPlay;
    }
    private List<Integer> lastXPlay;
    public List<Integer> getLastXPlay() {
        return lastXPlay;
    }


    private int sizeX = 11;
    public int getSizeX() {
        return sizeX;
    }
    private int sizeY = 8;
    public int getSizeY() {
        return sizeY;
    }

    protected GrillePowerMush(){

        lastXPlay = new ArrayList<>();
        lastYPlay = new ArrayList<>();

        this.grille = new ArrayList<>();

        this.grille.clear();
        for (int x = 0; x < sizeX; x++) {
            ArrayList<Joueur> tmpY = new ArrayList<Joueur>();
            for (int y = 0; y < sizeY; y++) {

                tmpY.add(Joueur.AUCUN);
            }
            this.grille.add(tmpY);
        }
    }

    public boolean placeItem(int x, Joueur player){
        if(x < sizeX && x >= 0) {
            List<Joueur> y = this.grille.get(x);
            int indexY = y.size() - 1;
            while (indexY >= 0 && y.get(indexY) != Joueur.AUCUN) {
                indexY--;
            }
            if (indexY < 0) {
                System.out.println("Colone pleine");
                return false;
            } else {
                y.set(indexY, player);
                lastYPlay.add(0, indexY);
                lastXPlay.add(0, x);
                return true;
            }
        }
        return false;
    }

    public int compteCasesVide(){
        int compt = 0;
        for(List<Joueur> x : this.grille){
            for(Joueur y : x){
                if(y == Joueur.AUCUN) compt++;
            }
        }
        return compt;
    }
}
