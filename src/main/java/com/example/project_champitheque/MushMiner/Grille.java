package com.example.project_champitheque.MushMiner;

import java.util.ArrayList;
import java.util.List;

public class Grille {

    private List<List<Case>> grille;

    private int sizeX;
    private int sizeY;


    public Grille(int sizeX, int sizeY, int difficulty){

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
            ArrayList<Case> tmpX = new ArrayList<Case>();
            for (int x = 0; x < this.sizeX; x++) {
                double value = Math.random();
                Case caseGrille;
                ValueCase defautValue = ValueCase.DEFAULT1;
                if(Math.random() <= 0.2){
                    defautValue = ValueCase.DEFAULT2;
                } else if (Math.random() <= 0.4) {
                    defautValue = ValueCase.DEFAULT3;
                } else if (Math.random() <= 0.6) {
                    defautValue = ValueCase.DEFAULT4;
                }

                if(value < randDifficulty){
                    caseGrille = new Case(ValueCase.CHAMPI, defautValue);
                }
                else{
                    caseGrille = new Case(ValueCase.VIDE, defautValue);
                }
                tmpX.add(caseGrille);
            }
            grille.add(tmpX);
        }

        compterChampisAutour();
    }


    protected ValueCase revealCase(int x, int y){
        if(isInGrille(x, y)){
            Case caseToReveal = grille.get(y).get(x);
            //On vérifie que la case n'a pas été révélée pour éviter les clics multiples
            if(!caseToReveal.isDiscover()){
                caseToReveal.setDiscover();
                caseToReveal.setLocked(false);
                return caseToReveal.getValue();
            }
            return ValueCase.VIDE;
        }
        return ValueCase.VIDE;
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
                Case caseToCalcul = grille.get(y).get(x);
                caseToCalcul.setChampiAutour(result);
            }
        }
    }

    private int compteOneCase(int x, int y){
        if(isInGrille(x, y)){
            int result = 0;
            if(grille.get(y).get(x).getValue() == ValueCase.CHAMPI){
                result = 1;
            }
            if(grille.get(y).get(x).getValue() == ValueCase.JACKPOT){
                result = 5;
            }
            if(grille.get(y).get(x).getValue() == ValueCase.BAD){
                result = 4;
            }
            return result;
        }
        return 0;
    }

    protected void lockCase(int x, int y){
        if(isInGrille(x, y)){
            Case caseToReveal = grille.get(y).get(x);
            if(!caseToReveal.isDiscover()){
                //On inverse
                caseToReveal.setLocked(!caseToReveal.getLocked());
            }
        }
    }

    protected boolean setEngraisCase(ValueCase nouvelleValeur, int x, int y){
        if(isInGrille(x, y)){
            Case caseToReset = grille.get(y).get(x);
            if(!caseToReset.isDiscover()){
                caseToReset.resetValue(nouvelleValeur);

                compterChampisAutour();
                return true;
            }
            return false;
        }
        return false;
    }

    public List<List<CaseToDisplay>> showGrille(){
        List<List<CaseToDisplay>> grilleToReturn = new ArrayList<>();
        for(List<Case> y : grille){
            List<CaseToDisplay> tmpGrilleToReturn = new ArrayList<>();
            for(Case x : y){
                if(x.isDiscover()){
                    tmpGrilleToReturn.add(new CaseToDisplay(x.getValue(), x.getLocked(), x.getChampiAutour()));
                }
                else if(x.getLocked()) {
                    tmpGrilleToReturn.add(new CaseToDisplay(x.defaultValue, true, 0));
                }
                else {
                    tmpGrilleToReturn.add(new CaseToDisplay(x.defaultValue, false, 0));
                }
            }
            grilleToReturn.add(tmpGrilleToReturn);
        }
        return grilleToReturn;
    }


    public List<List<CaseToDisplay>> showGrilleEnd(){
        List<List<CaseToDisplay>> grilleToReturn = new ArrayList<>();
        for(List<Case> y : grille){
            List<CaseToDisplay> tmpGrilleToReturn = new ArrayList<>();
            for(Case x : y){
                //On découvre tout pour obtenir les valeurs
                x.setDiscover();
                tmpGrilleToReturn.add(new CaseToDisplay(x.getValue(), x.getLocked(), x.getChampiAutour()));
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
        for(List<Case> y : grille) {
            for (Case x : y) {
                if(!x.isDiscover() && x.getValue() == ValueCase.CHAMPI){
                    nbChampi++;
                }
            }
        }
        return nbChampi;
    }

    protected int getChampiDiscovered(){
        int nbChampi = 0;
        for(List<Case> y : grille) {
            for (Case x : y) {
                if(x.isDiscover() && x.getValue() == ValueCase.CHAMPI){
                    nbChampi++;
                }
            }
        }
        return nbChampi;
    }


}
