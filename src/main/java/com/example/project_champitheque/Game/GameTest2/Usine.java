package com.example.project_champitheque.Game.GameTest2;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

public abstract class Usine extends Thread {

    protected boolean alive = true;
    protected final IntegerProperty champi = new SimpleIntegerProperty();
    public IntegerProperty champiProperty() {
        return champi;
    }


    protected TypeUsine type;
    public TypeUsine getType() {
        return type;
    }


    protected final IntegerProperty cost = new SimpleIntegerProperty();
    public int getCost() {
        return cost.get();
    }
    public IntegerProperty costProperty() {
        return cost;
    }


    protected String image;
    public String getImage() {
        return image;
    }


    protected final IntegerProperty gain = new SimpleIntegerProperty();
    public IntegerProperty gainProperty() {
        return gain;
    }

    protected final IntegerProperty level = new SimpleIntegerProperty();
    public IntegerProperty levelProperty() {
        return level;
    }

    public int lvlMax;


    public abstract Usine startItem();

    public abstract void run();

    public void sellUsine(){
        this.alive = false;
    }

    public int collect(){
        int tmp = champi.get();
        champi.setValue(0);
        return tmp;
    }

    public void increaseManual(){
        champi.setValue(champi.getValue()+gain.get()/2);
    }

    public int upgrade(){
        int tmpCost = 0;
        if(level.get() < lvlMax) {
            tmpCost = cost.get();
            level.setValue(level.get() + 1);
            gain.setValue(gain.get() * level.get());
            cost.setValue(cost.get() * level.get() * 1.3);
        }

        return tmpCost;
    }
}
