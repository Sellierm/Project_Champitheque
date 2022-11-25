package com.example.project_champitheque.Game.MushMiner;

public class BoxMushMiner {

    private BoxValueMushMiner value;
    protected BoxValueMushMiner getValue() {
        return value;
    }

    public BoxValueMushMiner defaultValue;

    private boolean discover;
    public boolean isDiscover() {
        return discover;
    }
    protected void setDiscover() {
        this.discover = true;
    }
    protected void resetDiscover() {
        this.discover = false;
    }

    private boolean locked;
    public boolean getLocked() {
        return locked;
    }
    protected void setLocked(boolean lock){ this.locked = lock;}

    private int champiAutour = 0;
    protected void setChampiAutour(int value){this.champiAutour = value;}
    protected int getChampiAutour(){return this.champiAutour;}

    public BoxMushMiner(BoxValueMushMiner valeurCase, BoxValueMushMiner defaultValue){
        this.value = valeurCase;
        this.defaultValue = defaultValue;
        this.discover = false;
        this.locked = false;
    }

    public void resetValue(BoxValueMushMiner nouvelleValeur){
        this.value = nouvelleValeur;
    }

}
