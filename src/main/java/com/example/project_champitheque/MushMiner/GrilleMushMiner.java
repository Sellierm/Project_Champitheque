package com.example.project_champitheque.MushMiner;

import java.util.ArrayList;
import java.util.List;

public class GrilleMushMiner {

    private List<List<BoxMushMiner>> grille;

    private int sizeX;
    private int sizeY;


    public GrilleMushMiner(int sizeX, int sizeY, int difficulty){

        if(sizeX >= 7 && sizeX <= 18){
            this.sizeX = sizeX;
        }
        else {
            this.sizeX = 15;
        }
        if(sizeY >= 10 && sizeY <= 12){
            this.sizeY = sizeY;
        }
        else {
            this.sizeY = 10;
        }

        this.grille = new ArrayList<>();


        double randDifficulty = 0.9;
        switch (difficulty){
            case 1:
                randDifficulty = 0.6;
                break;
            case 2:
                randDifficulty = 0.4;
                break;
            case 3:
                randDifficulty = 0.2;
                break;
            default:
                break;
        }


        for (int y = 0; y < this.sizeY; y++) {
            ArrayList<BoxMushMiner> tmpX = new ArrayList<BoxMushMiner>();
            for (int x = 0; x < this.sizeX; x++) {
                double value = Math.random();
                BoxMushMiner boxMushMinerGrille;
                BoxValueMushMiner defautValue = BoxValueMushMiner.DEFAULT1;
                if(Math.random() <= 0.2){
                    defautValue = BoxValueMushMiner.DEFAULT2;
                } else if (Math.random() <= 0.4) {
                    defautValue = BoxValueMushMiner.DEFAULT3;
                } else if (Math.random() <= 0.6) {
                    defautValue = BoxValueMushMiner.DEFAULT4;
                }

                if(value < randDifficulty){
                    boxMushMinerGrille = new BoxMushMiner(BoxValueMushMiner.CHAMPI, defautValue);
                }
                else{
                    boxMushMinerGrille = new BoxMushMiner(BoxValueMushMiner.VIDE, defautValue);
                }
                tmpX.add(boxMushMinerGrille);
            }
            grille.add(tmpX);
        }

        compterChampisAutour();
    }


    protected BoxValueMushMiner revealCase(int x, int y){
        if(isInGrille(x, y)){
            BoxMushMiner boxMushMinerToReveal = grille.get(y).get(x);
            //On vérifie que la case n'a pas été révélée pour éviter les clics multiples
            if(!boxMushMinerToReveal.isDiscover()){
                boxMushMinerToReveal.setDiscover();
                boxMushMinerToReveal.setLocked(false);
                return boxMushMinerToReveal.getValue();
            }
            return BoxValueMushMiner.VIDE;
        }
        return BoxValueMushMiner.VIDE;
    }

    protected boolean isRevealedCase(int x, int y){
        if(isInGrille(x, y)){
            return grille.get(y).get(x).isDiscover();
        }
        return false;
    }


    private void compterChampisAutour(){
        for(int i = 0; i< grille.size(); i++) {
            for (int j = 0; j < grille.get(i).size(); j++) {
                int x = j%grille.get(i).size();
                int y = i;
                int result = compteOneCase(x - 1, y - 1) + compteOneCase(x, y - 1) + compteOneCase(x + 1, y - 1)
                        + compteOneCase(x - 1, y) + compteOneCase(x + 1, y)
                        + compteOneCase(x - 1, y + 1) + compteOneCase(x, y + 1) + compteOneCase(x + 1, y + 1);
                BoxMushMiner boxMushMinerToCalcul = grille.get(y).get(x);
                boxMushMinerToCalcul.setChampiAutour(result);
            }
        }
    }

    private int compteOneCase(int x, int y){
        if(isInGrille(x, y)){
            int result = 0;
            if(grille.get(y).get(x).getValue() == BoxValueMushMiner.CHAMPI){
                result = 1;
            }
            if(grille.get(y).get(x).getValue() == BoxValueMushMiner.JACKPOT){
                result = 5;
            }
            if(grille.get(y).get(x).getValue() == BoxValueMushMiner.BAD){
                result = 4;
            }
            return result;
        }
        return 0;
    }

    protected void lockCase(int x, int y){
        if(isInGrille(x, y)){
            BoxMushMiner boxMushMinerToReveal = grille.get(y).get(x);
            if(!boxMushMinerToReveal.isDiscover()){
                //On inverse
                boxMushMinerToReveal.setLocked(!boxMushMinerToReveal.getLocked());
            }
        }
    }

    protected boolean setEngraisCase(BoxValueMushMiner nouvelleValeur, int x, int y){
        if(isInGrille(x, y)){
            BoxMushMiner boxMushMinerToReset = grille.get(y).get(x);
            if(!boxMushMinerToReset.isDiscover()){
                boxMushMinerToReset.resetValue(nouvelleValeur);

                compterChampisAutour();
                return true;
            }
            return false;
        }
        return false;
    }

    public List<List<BoxMushMinerToDisplay>> showGrille(){
        List<List<BoxMushMinerToDisplay>> grilleToReturn = new ArrayList<>();
        for(List<BoxMushMiner> y : grille){
            List<BoxMushMinerToDisplay> tmpGrilleToReturn = new ArrayList<>();
            for(BoxMushMiner x : y){
                if(x.isDiscover()){
                    tmpGrilleToReturn.add(new BoxMushMinerToDisplay(x.getValue(), x.getLocked(), x.getChampiAutour()));
                }
                else if(x.getLocked()) {
                    tmpGrilleToReturn.add(new BoxMushMinerToDisplay(x.defaultValue, true, 0));
                }
                else {
                    tmpGrilleToReturn.add(new BoxMushMinerToDisplay(x.defaultValue, false, 0));
                }
            }
            grilleToReturn.add(tmpGrilleToReturn);
        }
        return grilleToReturn;
    }


    public List<List<BoxMushMinerToDisplay>> showGrilleEnd(){
        List<List<BoxMushMinerToDisplay>> grilleToReturn = new ArrayList<>();
        for(List<BoxMushMiner> y : grille){
            List<BoxMushMinerToDisplay> tmpGrilleToReturn = new ArrayList<>();
            for(BoxMushMiner x : y){
                //On découvre tout pour obtenir les valeurs
                x.setDiscover();
                tmpGrilleToReturn.add(new BoxMushMinerToDisplay(x.getValue(), x.getLocked(), x.getChampiAutour()));
            }
            grilleToReturn.add(tmpGrilleToReturn);
        }
        return grilleToReturn;
    }



    private boolean isInGrille(int x, int y){
        return (x >= 0 && x < sizeX && y >= 0 && y < sizeY);
    }


    protected int getChampiToDiscover(){
        int nbChampi = 0;
        for(List<BoxMushMiner> y : grille) {
            for (BoxMushMiner x : y) {
                if(!x.isDiscover() && x.getValue() == BoxValueMushMiner.CHAMPI){
                    nbChampi++;
                }
            }
        }
        return nbChampi;
    }

    protected int getChampiDiscovered(){
        int nbChampi = 0;
        for(List<BoxMushMiner> y : grille) {
            for (BoxMushMiner x : y) {
                if(x.isDiscover() && x.getValue() == BoxValueMushMiner.CHAMPI){
                    nbChampi++;
                }
            }
        }
        return nbChampi;
    }


}
