package com.example.project_champitheque.ChampiMerge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrilleChampiMerge {

    private List<List<BoxChampiMerge>> grille;

    private int size;


    public GrilleChampiMerge(int size){
        if(size >= 4 && size <= 8){
            this.size = size;
        }
        else {
            this.size = 4;
        }

        this.grille = new ArrayList<>();

        for (int y = 0; y < this.size; y++) {
            List<BoxChampiMerge> tmpX = new ArrayList<BoxChampiMerge>();
            for (int x = 0; x < this.size; x++) {
                tmpX.add(new BoxChampiMerge(0));
            }
            grille.add(tmpX);
        }

        generateNewNumber();
        System.out.println(this.grille);
    }



    public void generateNewNumber(){
        Random rand = new Random();
        List<BoxChampiMerge> emptyCases = getEmptyCases();

        int minNbNewNumber = 1;
        int maxNbNewNumber = 1 + (int)(emptyCases.size()/2);
        int randNbNewNumber = rand.nextInt(maxNbNewNumber - minNbNewNumber + 1) + minNbNewNumber;

        for(int i = 0; i < randNbNewNumber; i++){

            int minIndex = 0;
            int maxIndex = emptyCases.size() - 1;
            int randIndex = rand.nextInt(maxIndex - minIndex + 1) + minIndex;

            emptyCases.get(randIndex).newValue(2);
        }
    }


    private List<BoxChampiMerge> getEmptyCases(){
        List<BoxChampiMerge> cooVides = new ArrayList<>();

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(grille.get(i).get(j).getValue() == 0){
                    cooVides.add(grille.get(i).get(j));
                }
            }
        }


        return cooVides;
    }


    public List<List<Integer>> showGrille(){
        List<List<Integer>> grilleToReturn = new ArrayList<>();
        for(List<BoxChampiMerge> y : grille){
            List<Integer> tmpGrilleToReturn = new ArrayList<>();
            for(BoxChampiMerge x : y){
                tmpGrilleToReturn.add(x.getValue());
            }
            grilleToReturn.add(tmpGrilleToReturn);
        }
        return grilleToReturn;
    }


    protected boolean checkEnd(){
        if(getEmptyCases().size() == 0){
            return true;
        }
        return false;
    }

    protected int getScore(){
        int total = 0;
        for(List<BoxChampiMerge> y : grille){
            for(BoxChampiMerge x : y){
                total+=x.getValue();
            }
        }
        return total;
    }


    protected boolean goDown(){
        putAllDown();
        for(int x = 0; x < size; x++){
            for (int y = size - 1; y > 0; y--){
                BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
                BoxChampiMerge prevBoxChampiMerge = grille.get(y - 1).get(x);
                if(oneBoxChampiMerge.getValue() == prevBoxChampiMerge.getValue()){
                    prevBoxChampiMerge.newValue(0);
                    oneBoxChampiMerge.newValue(oneBoxChampiMerge.getValue()*2);
                }
                putColDown(x);
            }
        }
        //On evite de générer des nouveaux nombres si le tableau est toujours plein
        //S'il est toujours plein après un coup c'est que la parti est fini
        // (enfin pas vraiment car c'est peut-etre juste le mauvais coup mais flemme de faire une détection plus complexe
        boolean isEnd = checkEnd();
        if(!isEnd)generateNewNumber();

        return isEnd;
    }
    private void putAllDown(){
        for(int x = 0; x < size; x++){
            for (int y = size - 1; y >= 0; y--){
                BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
                int newIndexY = y;
                while (newIndexY < size - 1 && grille.get(newIndexY + 1).get(x).getValue() == 0){
                    newIndexY ++;
                }
                if(newIndexY != y){
                    grille.get(newIndexY).get(x).newValue(oneBoxChampiMerge.getValue());
                    oneBoxChampiMerge.newValue(0);
                }
            }
        }
    }
    private void putColDown(int x){
        for (int y = size - 1; y >= 0; y--){
            BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
            int newIndexY = y;
            while (newIndexY < size - 1 && grille.get(newIndexY + 1).get(x).getValue() == 0){
                newIndexY ++;
            }
            if(newIndexY != y){
                grille.get(newIndexY).get(x).newValue(oneBoxChampiMerge.getValue());
                oneBoxChampiMerge.newValue(0);
            }
        }
    }



    protected boolean goUp(){
        putAllUp();
        for(int x = 0; x < size; x++){
            for (int y = 0; y < size-1; y++){
                BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
                BoxChampiMerge nextBoxChampiMerge = grille.get(y + 1).get(x);
                if(oneBoxChampiMerge.getValue() == nextBoxChampiMerge.getValue()){
                    nextBoxChampiMerge.newValue(0);
                    oneBoxChampiMerge.newValue(oneBoxChampiMerge.getValue()*2);
                }
                putColUp(x);

            }
        }
        boolean isEnd = checkEnd();
        if(!isEnd)generateNewNumber();

        return isEnd;
    }
    private void putAllUp(){
        for(int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
                int newIndexY = y;
                while (newIndexY > 0 && grille.get(newIndexY - 1).get(x).getValue() == 0){
                    newIndexY --;
                }
                if(newIndexY != y){
                    grille.get(newIndexY).get(x).newValue(oneBoxChampiMerge.getValue());
                    oneBoxChampiMerge.newValue(0);
                }
            }
        }
    }
    private void putColUp(int x){
        for (int y = 0; y < size; y++){
            BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
            int newIndexY = y;
            while (newIndexY > 0 && grille.get(newIndexY - 1).get(x).getValue() == 0){
                newIndexY --;
            }
            if(newIndexY != y){
                grille.get(newIndexY).get(x).newValue(oneBoxChampiMerge.getValue());
                oneBoxChampiMerge.newValue(0);
            }
        }
    }


    protected boolean goLeft(){
        putAllLeft();
        for(int y = 0; y < size; y++){
            for (int x = 0; x < size-1; x++){
                BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
                BoxChampiMerge nextBoxChampiMerge = grille.get(y).get(x + 1);
                if(oneBoxChampiMerge.getValue() == nextBoxChampiMerge.getValue()){
                    nextBoxChampiMerge.newValue(0);
                    oneBoxChampiMerge.newValue(oneBoxChampiMerge.getValue()*2);
                }
                putRowLeft(y);
            }
        }
        boolean isEnd = checkEnd();
        if(!isEnd)generateNewNumber();

        return isEnd;
    }
    private void putAllLeft(){
        for(int y = 0; y < size; y++){
            for (int x = 0; x < size; x++){
                BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
                int newIndexX = x;
                while (newIndexX > 0 && grille.get(y).get(newIndexX - 1).getValue() == 0){
                    newIndexX --;
                }
                if(newIndexX != x){
                    grille.get(y).get(newIndexX).newValue(oneBoxChampiMerge.getValue());
                    oneBoxChampiMerge.newValue(0);
                }
            }
        }
    }
    private void putRowLeft(int y){
        for (int x = 0; x < size; x++){
            BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
            int newIndexX = x;
            while (newIndexX > 0 && grille.get(y).get(newIndexX - 1).getValue() == 0){
                newIndexX --;
            }
            if(newIndexX != x){
                grille.get(y).get(newIndexX).newValue(oneBoxChampiMerge.getValue());
                oneBoxChampiMerge.newValue(0);
            }
        }
    }




    protected boolean goRight(){
        putAllRight();
        for(int y = 0; y < size; y++){
            for (int x = size - 1; x > 0; x--){
                BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
                BoxChampiMerge nextBoxChampiMerge = grille.get(y).get(x - 1);
                if(oneBoxChampiMerge.getValue() == nextBoxChampiMerge.getValue()){
                    nextBoxChampiMerge.newValue(0);
                    oneBoxChampiMerge.newValue(oneBoxChampiMerge.getValue()*2);
                }
                putRowRight(y);
            }
        }
        boolean isEnd = checkEnd();
        if(!isEnd)generateNewNumber();

        return isEnd;
    }
    private void putAllRight(){
        for(int y = 0; y < size; y++){
            for (int x = size - 1; x >= 0; x--){
                BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
                int newIndexX = x;
                while (newIndexX < size - 1 && grille.get(y).get(newIndexX + 1).getValue() == 0){
                    newIndexX ++;
                }
                if(newIndexX != x){
                    grille.get(y).get(newIndexX).newValue(oneBoxChampiMerge.getValue());
                    oneBoxChampiMerge.newValue(0);
                }
            }
        }
    }
    private void putRowRight(int y){
        for (int x = size - 1; x >= 0; x--){
            BoxChampiMerge oneBoxChampiMerge = grille.get(y).get(x);
            int newIndexX = x;
            while (newIndexX < size - 1 && grille.get(y).get(newIndexX + 1).getValue() == 0){
                newIndexX ++;
            }
            if(newIndexX != x){
                grille.get(y).get(newIndexX).newValue(oneBoxChampiMerge.getValue());
                oneBoxChampiMerge.newValue(0);
            }
        }
    }
}
