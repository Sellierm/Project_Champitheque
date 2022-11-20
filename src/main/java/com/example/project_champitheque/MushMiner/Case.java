package com.example.project_champitheque.MushMiner;

public class Case {

    private ValueCase value;
    protected ValueCase getValue() {
        return value;
    }

    public ValueCase defaultValue;

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

    public Case(ValueCase valeurCase, ValueCase defaultValue){
        this.value = valeurCase;
        this.defaultValue = defaultValue;
        this.discover = false;
        this.locked = false;
    }

    public void resetValue(ValueCase nouvelleValeur){
        this.value = nouvelleValeur;
    }

}
