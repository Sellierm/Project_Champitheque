package com.example.project_champitheque.Game.MushIdle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

public abstract class Usine extends Thread {

    protected boolean alive = true;
    protected final LongProperty champi = new SimpleLongProperty();
    public LongProperty champiProperty() {
        return champi;
    }


    protected TypeUsine type;
    public TypeUsine getType() {
        return type;
    }


    protected final LongProperty cost = new SimpleLongProperty();
    public long getCost() {
        return cost.get();
    }
    public LongProperty costProperty() {
        return cost;
    }


    protected final LongProperty upagradeCost = new SimpleLongProperty();
    public long getUpagradeCost() {
        return upagradeCost.get();
    }
    public LongProperty upagradeCostProperty() {
        return upagradeCost;
    }


    protected static int mult;


    protected String image;
    public String getImage() {
        return image;
    }


    protected final LongProperty gain = new SimpleLongProperty();
    public LongProperty gainProperty() {
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

    public long collect(){
        long tmp = champi.get();
        champi.setValue(0);
        return tmp;
    }

    public void increaseManual(){
        champi.setValue(champi.getValue()+gain.get()/2);
    }

    public long upgrade(){
        long tmpCost = 0;
        if(level.get() < lvlMax) {
            tmpCost = upagradeCost.get();
            level.setValue(level.get() + 1);
            gain.setValue(gain.get() * 1.5);
            upagradeCost.setValue(upagradeCost.get() + mult*level.get());
        }

        return tmpCost;
    }
}
